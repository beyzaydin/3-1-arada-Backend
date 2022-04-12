package winx.bitirme.achievement.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.achievement.service.entity.UserAchievementEntity;

import java.util.List;

@Repository
public interface UserAchievementRepository extends MongoRepository<UserAchievementEntity, Long> {
    UserAchievementEntity findByEmailAndDescription(String email, String achievementType);
    UserAchievementEntity findByEmailAndAchievementType(String email, String achievementType);
    List<UserAchievementEntity> findAllByEmail(String email);
    List<UserAchievementEntity> findAllByAchievementType(String achievementType);
    List<UserAchievementEntity> findAllByAchievementTypeAndIsCompleted(String achievementType, Boolean isCompleted);
}
