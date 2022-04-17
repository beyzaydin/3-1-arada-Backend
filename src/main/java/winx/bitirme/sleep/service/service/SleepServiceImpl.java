package winx.bitirme.sleep.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import winx.bitirme.mongo.service.logic.SequenceGeneratorService;
import winx.bitirme.sleep.client.model.SleepStatisticModel;
import winx.bitirme.sleep.client.model.SleepWebResponse;
import winx.bitirme.sleep.service.entity.DailyStatisticEntity;
import winx.bitirme.sleep.service.repository.DailyStatisticRepository;
import winx.bitirme.sleep.service.repository.SleepStatisticRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

    @Autowired
    public SleepServiceImpl(DailyStatisticRepository dailyStatisticRepository,
                            SleepStatisticRepository sleepStatisticRepository,
                            SequenceGeneratorService sequenceGeneratorService,
                            MongoTemplate mongoTemplate) {
        this.dailyStatisticRepository = dailyStatisticRepository;
        this.sleepStatisticRepository = sleepStatisticRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public DailyStatisticEntity saveSleepData(List<SleepStatisticModel> list) {
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

    @Override
    public List<DailyStatisticEntity> getWeeklyDataForMobile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        dayOfWeek = dayOfWeek == 0 ? 7 : dayOfWeek;

        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        Date endTime = end.getTime();

        end.add(Calendar.DAY_OF_WEEK, -(dayOfWeek + 1));
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);
        Date startTime = end.getTime();

        List<DailyStatisticEntity> list = dailyStatisticRepository.findAllByEmail(email);
        List<DailyStatisticEntity> result = list.stream()
                .filter(el -> el.getSleepStartTime().after(startTime) && el.getSleepStartTime().before(endTime))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public SleepWebResponse getWeeklyDataForWeb(int weekCount) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        dayOfWeek = dayOfWeek == 0 ? 7 : dayOfWeek;

        Calendar end = Calendar.getInstance();

        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.add(Calendar.DAY_OF_WEEK, -(dayOfWeek + 7 * weekCount - 1));
        Date startTime = end.getTime();

        end.add(Calendar.DAY_OF_WEEK, 6);
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);

        Date endTime = end.getTime();

        List<DailyStatisticEntity> list = dailyStatisticRepository.findAllByEmail(email);
        List<DailyStatisticEntity> result = list.stream()
                .filter(el -> el.getSleepStartTime().after(startTime) && el.getSleepStartTime().before(endTime))
                .collect(Collectors.toList());
        SleepWebResponse sleepWebResponse = new SleepWebResponse();
        List<Boolean> validDays = new ArrayList<>(7);

        Instant instant1 = startTime.toInstant()
                .truncatedTo(ChronoUnit.DAYS);

        for (int i = 0; i <= 6; i++) {
            validDays.add(false);
            for (int j = 0; j < result.size(); j++) {
                Instant instant2 = result.get(j).getSleepStartTime().toInstant()
                        .truncatedTo(ChronoUnit.DAYS);
                boolean isEqual = instant1.equals(instant2);
                if (isEqual) {
                    validDays.set(i,true);
                    break;
                }
            }
            instant1 = instant1.plus(1, ChronoUnit.DAYS);
        }

        List<DailyStatisticEntity> finalList = new ArrayList<>(7);
        for (int i = 0; i < 7; i++) {
            finalList.add(null);
            if(validDays.get(i)){
                finalList.set(i,result.get(0));
                result.remove(0);
            }
        }
        sleepWebResponse.setList(finalList);
        sleepWebResponse.setValidDays(validDays);
        return sleepWebResponse;
    }

    /*
     * Burada 1 ile 10 arasına dağıttım verileri.
     * dailyStatistic'i de setledim.
     * Yukarıda sadece list'i alıp methoda verilecek
     * */
    private DailyStatisticEntity normalise(List<SleepStatisticModel> models) {
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
