package winx.bitirme.sleep.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import winx.bitirme.mongo.service.logic.SequenceGeneratorService;
import winx.bitirme.sleep.client.model.SleepStatisticModel;
import winx.bitirme.sleep.service.entity.DailyStatisticEntity;
import winx.bitirme.sleep.service.repository.DailyStatisticRepository;
import winx.bitirme.sleep.service.repository.SleepStatisticRepository;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
public class SleepServiceImpl implements SleepService {
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private final DailyStatisticRepository dailyStatisticRepository;
    private final SleepStatisticRepository sleepStatisticRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final MongoTemplate mongoTemplate;
    private final MongoOperations mongoOperations;

    @Autowired
    public SleepServiceImpl(DailyStatisticRepository dailyStatisticRepository,
                            SleepStatisticRepository sleepStatisticRepository,
                            SequenceGeneratorService sequenceGeneratorService,
                            MongoTemplate mongoTemplate, MongoOperations mongoOperations) {
        this.dailyStatisticRepository = dailyStatisticRepository;
        this.sleepStatisticRepository = sleepStatisticRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.mongoTemplate = mongoTemplate;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public DailyStatisticEntity saveSleepData(List<SleepStatisticModel> list) throws ParseException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String text = date.format(formatters);
        LocalDate parsedDate = LocalDate.parse(text, formatters);

        DailyStatisticEntity fromDb = dailyStatisticRepository.findByEmailAndDate(email, parsedDate);

        if (fromDb == null) {
            DailyStatisticEntity entity = normalise(list);
            entity.setEmail(email);
            entity.setDate(parsedDate);
            return dailyStatisticRepository.save(entity);
        }
        return fromDb;
    }

    //TODO burayı adnanla dene
    @Override
    public List<DailyStatisticEntity> getWeeklyDataForMobile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        Date endTime = end.getTime();

        end.add(Calendar.DAY_OF_WEEK, -dayOfWeek);
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);
        Date startTime = end.getTime();

        List<DailyStatisticEntity> list = dailyStatisticRepository.findAllByEmail(email);
        List<DailyStatisticEntity> result = list.stream().filter(el -> el.getSleepStartTime().after(startTime) && el.getSleepStartTime().before(endTime)).collect(Collectors.toList());
        return result;
    }

    /*
     * Burada 1 ile 10 arasına dağıttım verileri.
     * dailyStatistic'i de setledim.
     * Yukarıda sadece list'i alıp methoda verilecek
     * */
    private DailyStatisticEntity normalise(List<SleepStatisticModel> models) throws ParseException {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        int minInd = 0, maxInd = 0;
        for (SleepStatisticModel el : models) {
            if (el.getFrequency() <= min) {
                min = el.getFrequency();
                minInd = models.indexOf(el);
            }
            if (el.getFrequency() >= max) {
                max = el.getFrequency();
                maxInd = models.indexOf(el);
            }
        }

        long id = sequenceGeneratorService.generateSequence(DailyStatisticEntity.SEQUENCE_NAME);
        DailyStatisticEntity dailyStatisticEntity = new DailyStatisticEntity();
        dailyStatisticEntity.setId(id);
        dailyStatisticEntity.setSleepStartTime(models.get(0).getDate());
        dailyStatisticEntity.setSleepEndTime(models.get(models.size() - 1).getDate());
        dailyStatisticEntity.setBestSleepAt(models.get(maxInd).getDate());
        dailyStatisticEntity.setWorstSleepAt(models.get(minInd).getDate());
        long diffInMillies = Math.abs(models.get(models.size() - 1).getDate().getTime() - models.get(0).getDate().getTime());
        long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        dailyStatisticEntity.setTotalSleepHours((double) diff / 60.0);

        double finalMax = max;
        double finalMin = min;
        AtomicReference<Float> total = new AtomicReference<>((float) 0);
        models.forEach(el -> {
            el.setFrequency(10.0 - (el.getFrequency() - finalMin) / (finalMax - finalMin) * 9);
            total.updateAndGet(v -> (float) (v + el.getFrequency()));
        });

        dailyStatisticEntity.setAverageSleepQuality((double) (total.get() / (float) models.size()));
        dailyStatisticEntity.setSleepTimeList(models.stream().map(SleepStatisticModel::getDate).collect(Collectors.toList()));
        dailyStatisticEntity.setSleepQualityList(models.stream().map(SleepStatisticModel::getFrequency).collect(Collectors.toList()));
        return dailyStatisticEntity;
    }
}
