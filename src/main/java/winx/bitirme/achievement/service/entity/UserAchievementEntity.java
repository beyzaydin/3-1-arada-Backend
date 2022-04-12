package winx.bitirme.achievement.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import winx.bitirme.achievement.client.model.EnumAchievementType;

@Document(collection = "user-achievement")
public class UserAchievementEntity {
    public static final String SEQUENCE_NAME = "user_achievement_sequence";
    @Id
    private Long id;
    private String email;
    private EnumAchievementType achievementType;
    private String description;
    private Double percentage;
    private Boolean isCompleted;
    private Long goal;
    private Long occurred;

    public UserAchievementEntity(Long id, String email,
                                 EnumAchievementType achievementType,
                                 String description, Double percentage,
                                 Boolean isCompleted, Long goal,
                                 Long occurred) {
        this.id = id;
        this.email = email;
        this.achievementType = achievementType;
        this.description = description;
        this.percentage = percentage;
        this.isCompleted = isCompleted;
        this.goal = goal;
        this.occurred = occurred;
    }

    public UserAchievementEntity() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnumAchievementType getAchievementType() {
        return achievementType;
    }

    public void setAchievementType(EnumAchievementType achievementType) {
        this.achievementType = achievementType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public Long getGoal() {
        return goal;
    }

    public void setGoal(Long goal) {
        this.goal = goal;
    }

    public Long getOccurred() {
        return occurred;
    }

    public void setOccurred(Long occurred) {
        this.occurred = occurred;
    }
}
