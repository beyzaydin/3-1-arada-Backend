package winx.bitirme.achievement.service.logic;

import org.springframework.http.ResponseEntity;
import winx.bitirme.achievement.client.model.EnumAchievementType;
import winx.bitirme.achievement.client.model.UserAchievementModel;

public interface UserAchievementService {
    ResponseEntity save(UserAchievementModel model);
    ResponseEntity update(UserAchievementModel model);
    void deleteByEmailAndType(String email, EnumAchievementType achievementType);
    ResponseEntity getListByEmail(String email);
    void saveInitialEntities(String email);
    void dailyLoginAchieved(String email);
    void dailyAchievementRefresh();
}
