package winx.bitirme.sleep.service.entity;

import com.sun.istack.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.context.SecurityContextHolder;
import winx.bitirme.auth.service.logic.UserDetailsImpl;

import java.util.List;

@Document("sleepData")
public class SleepDataEntity {
    @Transient
    public static final String SEQUENCE_NAME = "sleep_data_sequence";

    @Id
    private Long id;

    @NotNull
    @Field(name = "user")
    @DBRef
    private UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    @DBRef
    @Field(name = "dailyStatistics")
    private List<DailyStatisticEntity> dailyStatisticEntityList;

    @Field(name = "averageSleepQuality")
    private Double averageSleepQuality;

    @Field(name = "averageSleepHoursPerDay")
    private Double averageSleepHoursPerDay;

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

    public List<DailyStatisticEntity> getDailyStatisticEntityList() {
        return dailyStatisticEntityList;
    }

    public void setDailyStatisticEntityList(List<DailyStatisticEntity> dailyStatisticEntityList) {
        this.dailyStatisticEntityList = dailyStatisticEntityList;
    }

    public Double getAverageSleepQuality() {
        return averageSleepQuality;
    }

    public void setAverageSleepQuality(Double averageSleepQuality) {
        this.averageSleepQuality = averageSleepQuality;
    }

    public Double getAverageSleepHoursPerDay() {
        return averageSleepHoursPerDay;
    }

    public void setAverageSleepHoursPerDay(Double averageSleepHoursPerDay) {
        this.averageSleepHoursPerDay = averageSleepHoursPerDay;
    }
}
