package winx.bitirme.achievement.client.model;

import javax.validation.constraints.NotNull;

public class UserAchievementModel {
    private Long id;
    private String email;
    @NotNull
    private EnumAchievementType achievementType;
    @NotNull
    private String description;
    @NotNull
    private Double percentage;
    private Boolean isCompleted;
    private Long goal;
    @NotNull
    private Long occurred;

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
