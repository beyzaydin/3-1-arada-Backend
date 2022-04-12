package winx.bitirme.achievement.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import winx.bitirme.achievement.client.model.EnumAchievementType;
import winx.bitirme.achievement.client.model.UserAchievementModel;
import winx.bitirme.achievement.service.converter.UserAchievementMapper;
import winx.bitirme.achievement.service.entity.UserAchievementEntity;
import winx.bitirme.achievement.service.repository.UserAchievementRepository;
import winx.bitirme.mongo.service.logic.SequenceGeneratorService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserAchievementServiceImpl implements UserAchievementService {
    private final UserAchievementRepository repository;
    private final UserAchievementMapper mapper;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public UserAchievementServiceImpl(UserAchievementRepository repository,
                                      UserAchievementMapper mapper,
                                      SequenceGeneratorService sequenceGeneratorService) {
        this.repository = repository;
        this.mapper = mapper;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }


    @Override
    public ResponseEntity save(UserAchievementModel model) {
        long id = sequenceGeneratorService.generateSequence(UserAchievementEntity.SEQUENCE_NAME);
        UserAchievementEntity entity = mapper.convertToEntity(model);
        entity.setId(id);
        return ResponseEntity.ok(mapper.convertToModel(repository.save(entity)));
    }

    @Override
    public ResponseEntity update(UserAchievementModel model) {
        UserAchievementEntity entity = repository.findByEmailAndDescription(model.getEmail(), model.getDescription());
        if (entity == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Achievement not found.");
        model.setId(model.getId());
        return ResponseEntity.ok(mapper.convertToModel(repository.save(mapper.convertToEntity(model))));
    }

    @Override
    public void deleteByEmailAndType(String email, EnumAchievementType achievementType) {
        UserAchievementEntity entity = repository.findByEmailAndDescription(email, achievementType.name());
        if (entity != null)
            repository.delete(entity);
    }

    @Override
    public ResponseEntity getListByEmail(String email) {
        return ResponseEntity.ok(mapper.convertToModelList(repository.findAllByEmail(email)));
    }

    @Override
    public void saveInitialEntities(String email) {
        List<UserAchievementEntity> list = repository.findAllByEmail(email);
        if (list.isEmpty() || list == null) {
            long id = sequenceGeneratorService.generateSequence(UserAchievementEntity.SEQUENCE_NAME);
            UserAchievementEntity entity = new UserAchievementEntity();
            entity.setId(id);
            entity.setAchievementType(EnumAchievementType.LOGIN_INTO_ACCOUNT);
            entity.setEmail(email);
            entity.setCompleted(false);
            entity.setGoal(1L);
            entity.setOccurred(0L);
            entity.setPercentage(0.0);
            entity.setDescription("Login into Account.");

            repository.save(entity);

            id = sequenceGeneratorService.generateSequence(UserAchievementEntity.SEQUENCE_NAME);
            entity = new UserAchievementEntity();
            entity.setId(id);
            entity.setAchievementType(EnumAchievementType.USE3IN1_FOR_THREE_MONTHS);
            entity.setEmail(email);
            entity.setCompleted(false);
            entity.setGoal(91L);
            entity.setOccurred(0L);
            entity.setPercentage(0.0);
            entity.setDescription("Use 3in1 for 3 Months.");

            repository.save(entity);

            id = sequenceGeneratorService.generateSequence(UserAchievementEntity.SEQUENCE_NAME);
            entity = new UserAchievementEntity();
            entity.setId(id);
            entity.setAchievementType(EnumAchievementType.USE3IN1_FOR_ONE_YEAR);
            entity.setEmail(email);
            entity.setCompleted(false);
            entity.setGoal(365L);
            entity.setOccurred(0L);
            entity.setPercentage(0.0);
            entity.setDescription("Use 3in1 for 1 Year.");

            repository.save(entity);

            id = sequenceGeneratorService.generateSequence(UserAchievementEntity.SEQUENCE_NAME);
            entity = new UserAchievementEntity();
            entity.setId(id);
            entity.setAchievementType(EnumAchievementType.USE_CHAT_MODULE_FOR_100_HOURS);
            entity.setEmail(email);
            entity.setCompleted(false);
            entity.setGoal(100L);
            entity.setOccurred(0L);
            entity.setPercentage(0.0);
            entity.setDescription("Use Chat for 100 Hours.");

            repository.save(entity);

            id = sequenceGeneratorService.generateSequence(UserAchievementEntity.SEQUENCE_NAME);
            entity = new UserAchievementEntity();
            entity.setId(id);
            entity.setAchievementType(EnumAchievementType.USE_SLEEP_MODULE_FOR_100_HOURS);
            entity.setEmail(email);
            entity.setCompleted(false);
            entity.setGoal(100L);
            entity.setOccurred(0L);
            entity.setPercentage(0.0);
            entity.setDescription("Use Sleep Module for 100 hours.");

            repository.save(entity);

            id = sequenceGeneratorService.generateSequence(UserAchievementEntity.SEQUENCE_NAME);
            entity = new UserAchievementEntity();
            entity.setId(id);
            entity.setAchievementType(EnumAchievementType.USE_SLEEP_MODULE_FOR_100_HOURS);
            entity.setEmail(email);
            entity.setCompleted(false);
            entity.setGoal(100L);
            entity.setOccurred(0L);
            entity.setPercentage(0.0);
            entity.setDescription("Use Sleep Module for 100 hours.");

            repository.save(entity);

            id = sequenceGeneratorService.generateSequence(UserAchievementEntity.SEQUENCE_NAME);
            entity = new UserAchievementEntity();
            entity.setId(id);
            entity.setAchievementType(EnumAchievementType.USE_MEDITATION_MODULE_FOR_30_MINUTES_DAILY);
            entity.setEmail(email);
            entity.setCompleted(false);
            entity.setGoal(30L);
            entity.setOccurred(0L);
            entity.setPercentage(0.0);
            entity.setDescription("Meditate for 30 minutes.");

            repository.save(entity);

            id = sequenceGeneratorService.generateSequence(UserAchievementEntity.SEQUENCE_NAME);
            entity = new UserAchievementEntity();
            entity.setId(id);
            entity.setAchievementType(EnumAchievementType.USE_SLEEP_MODULE_FOR_8_HOURS_DAILY);
            entity.setEmail(email);
            entity.setCompleted(false);
            entity.setGoal(480L);
            entity.setOccurred(0L);
            entity.setPercentage(0.0);
            entity.setDescription("Sleep for 8 hours.");

            repository.save(entity);

            id = sequenceGeneratorService.generateSequence(UserAchievementEntity.SEQUENCE_NAME);
            entity = new UserAchievementEntity();
            entity.setId(id);
            entity.setAchievementType(EnumAchievementType.COMPLETE_YOUR_SLEEP_GOAL_3_TIMES);
            entity.setEmail(email);
            entity.setCompleted(false);
            entity.setGoal(3L);
            entity.setOccurred(0L);
            entity.setPercentage(0.0);
            entity.setDescription("Sleep 8 hours 3 days.");

            repository.save(entity);

            id = sequenceGeneratorService.generateSequence(UserAchievementEntity.SEQUENCE_NAME);
            entity = new UserAchievementEntity();
            entity.setId(id);
            entity.setAchievementType(EnumAchievementType.COMPLETE_15_ITEMS_IN_TODO_LIST);
            entity.setEmail(email);
            entity.setCompleted(false);
            entity.setGoal(15L);
            entity.setOccurred(0L);
            entity.setPercentage(0.0);
            entity.setDescription("Complete 15 Items in your TODO List");

            repository.save(entity);
        }
    }

    @Override
    public void dailyLoginAchieved(String email) {
        UserAchievementEntity entity =
                repository.findByEmailAndAchievementType(email, EnumAchievementType.LOGIN_INTO_ACCOUNT.name());
        if (entity != null) {
            entity.setPercentage(1.0);
            entity.setOccurred(1L);
            entity.setCompleted(true);
            repository.save(entity);
        }
    }

    @Override
    public void dailyAchievementRefresh() {
        //todo optimize edilmeli
        repository.findAllByAchievementType(EnumAchievementType.LOGIN_INTO_ACCOUNT.name()).forEach(el -> {
            el.setCompleted(false);
            el.setOccurred(0L);
            el.setPercentage(0.0);
            repository.save(el);
        });

        repository.findAllByAchievementType(EnumAchievementType.USE_MEDITATION_MODULE_FOR_30_MINUTES_DAILY.name())
                .forEach(el -> {
                    el.setCompleted(false);
                    el.setOccurred(0L);
                    el.setPercentage(0.0);
                    repository.save(el);
                });

        repository.findAllByAchievementType(EnumAchievementType.USE_SLEEP_MODULE_FOR_8_HOURS_DAILY.name())
                .forEach(el -> {
                    el.setCompleted(false);
                    el.setOccurred(0L);
                    el.setPercentage(0.0);
                    repository.save(el);
                });

        repository.findAllByAchievementType(EnumAchievementType.USE_SLEEP_MODULE_FOR_8_HOURS_DAILY.name())
                .forEach(el -> {
                    el.setCompleted(false);
                    el.setOccurred(0L);
                    el.setPercentage(0.0);
                    repository.save(el);
                });

        repository.findAllByAchievementTypeAndIsCompleted(EnumAchievementType.USE3IN1_FOR_THREE_MONTHS.name(), false)
                .forEach(el -> {
                    el.setOccurred(el.getOccurred() + 1);
                    if (el.getGoal() == el.getOccurred()) {
                        el.setCompleted(true);
                        el.setPercentage(1.0);
                    } else {
                        double percentage = el.getOccurred() / el.getGoal();
                        el.setPercentage(percentage);
                    }
                    repository.save(el);
                });

        repository.findAllByAchievementTypeAndIsCompleted(EnumAchievementType.USE3IN1_FOR_ONE_YEAR.name(), false)
                .forEach(el -> {
                    el.setOccurred(el.getOccurred() + 1);
                    if (el.getGoal() == el.getOccurred()) {
                        el.setCompleted(true);
                        el.setPercentage(1.0);
                    } else {
                        double percentage = (double)el.getOccurred() / (double)el.getGoal();
                        el.setPercentage(percentage);
                    }
                    repository.save(el);
                });
    }
}
