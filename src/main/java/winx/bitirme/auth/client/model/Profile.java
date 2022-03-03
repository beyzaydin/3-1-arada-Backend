package winx.bitirme.auth.client.model;


import winx.bitirme.auth.service.logic.UserDetailsImpl;

public class Profile {
    private UserDetailsImpl userDetails;

    private Integer sleepProgress;

    private Integer meditationProgress;

    public UserDetailsImpl getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetailsImpl userDetails) {
        this.userDetails = userDetails;
    }

    public Integer getSleepProgress() {
        return sleepProgress;
    }

    public void setSleepProgress(Integer sleepProgress) {
        this.sleepProgress = sleepProgress;
    }

    public Integer getMeditationProgress() {
        return meditationProgress;
    }

    public void setMeditationProgress(Integer meditationProgress) {
        this.meditationProgress = meditationProgress;
    }
}
