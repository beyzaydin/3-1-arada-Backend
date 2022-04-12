package winx.bitirme.achievement.service.converter;

import org.mapstruct.Mapper;
import winx.bitirme.achievement.client.model.UserAchievementModel;
import winx.bitirme.achievement.service.entity.UserAchievementEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserAchievementMapper {
    public UserAchievementEntity convertToEntity(UserAchievementModel model);

    public UserAchievementModel convertToModel(UserAchievementEntity model);

    public List<UserAchievementEntity> convertToEntityList(List<UserAchievementModel> model);

    public List<UserAchievementModel> convertToModelList(List<UserAchievementEntity> model);
}
