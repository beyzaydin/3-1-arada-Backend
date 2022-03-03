package winx.bitirme.sleep.service.entity;

import com.sun.istack.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.context.SecurityContextHolder;
import winx.bitirme.auth.service.logic.UserDetailsImpl;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.List;

@Document("DailyStatistic")
public class DailyStatisticEntity {
    @Transient
    public static final String SEQUENCE_NAME = "daily_statistic_sequence";

    @Id
    private Long id;

    @NotNull
    @Field(name = "user")
    @DBRef
    private UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @Field("startTime")
    private OffsetDateTime sleepStartTime;

    @Field("endTime")
    private OffsetDateTime sleepEndTime;

    @Field("sleepQuality")
    private List<Long> sleepQuality;

    @Field("totalSleepTime")
    private List<Long> totalSleepTime;

    @Field("bestSleepAt")
    private OffsetTime bestSleepAt;

    @Field("worstSleepAt")
    private OffsetTime worstSleepAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDetailsImpl getUser() {
        return user;
    }

    public void setUser(UserDetailsImpl user) {
        this.user = user;
    }

    public OffsetDateTime getSleepStartTime() {
        return sleepStartTime;
    }

    public void setSleepStartTime(OffsetDateTime sleepStartTime) {
        this.sleepStartTime = sleepStartTime;
    }

    public OffsetDateTime getSleepEndTime() {
        return sleepEndTime;
    }

    public void setSleepEndTime(OffsetDateTime sleepEndTime) {
        this.sleepEndTime = sleepEndTime;
    }

    public List<Long> getSleepQuality() {
        return sleepQuality;
    }

    public void setSleepQuality(List<Long> sleepQuality) {
        this.sleepQuality = sleepQuality;
    }

    public List<Long> getTotalSleepTime() {
        return totalSleepTime;
    }

    public void setTotalSleepTime(List<Long> totalSleepTime) {
        this.totalSleepTime = totalSleepTime;
    }

    public OffsetTime getBestSleepAt() {
        return bestSleepAt;
    }

    public void setBestSleepAt(OffsetTime bestSleepAt) {
        this.bestSleepAt = bestSleepAt;
    }

    public OffsetTime getWorstSleepAt() {
        return worstSleepAt;
    }

    public void setWorstSleepAt(OffsetTime worstSleepAt) {
        this.worstSleepAt = worstSleepAt;
    }
}
