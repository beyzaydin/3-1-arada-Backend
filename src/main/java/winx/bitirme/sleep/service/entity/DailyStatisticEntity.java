package winx.bitirme.sleep.service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Document("daily-sleep-statistic")
public class DailyStatisticEntity {
    @Transient
    public static final String SEQUENCE_NAME = "daily_statistic_sequence";

    @Id
    private Long id;

    //ben koyuyorum
    private String email;

    //mobilden geliyor setliyorum
    private List<Date> sleepTimeList;
    //normalize edip koyuyorum
    private List<Double> sleepQualityList;
    //gelen ilk datayla bunu setliyorum
    @Field("startTime")
    private Date sleepStartTime;
    //gelen datanın son verisini alıyorum
    @Field("endTime")
    private Date sleepEndTime;
    //bunu setliyorum
    @Field("averageSleepQuality")
    private Double averageSleepQuality;
    //bunu setliyorum
    @Field("bestSleepAt")
    private Date bestSleepAt;
    //bunu setliyorum
    @Field("worstSleepAt")
    private Date worstSleepAt;
    //mobilden gelecek
    @Field("totalSleep")
    private Double totalSleepHours;
    //Ben setliyorum
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

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

    public Date getSleepStartTime() {
        return sleepStartTime;
    }

    public void setSleepStartTime(Date sleepStartTime) {
        this.sleepStartTime = sleepStartTime;
    }

    public Date getSleepEndTime() {
        return sleepEndTime;
    }

    public void setSleepEndTime(Date sleepEndTime) {
        this.sleepEndTime = sleepEndTime;
    }

    public Date getBestSleepAt() {
        return bestSleepAt;
    }

    public void setBestSleepAt(Date bestSleepAt) {
        this.bestSleepAt = bestSleepAt;
    }

    public Date getWorstSleepAt() {
        return worstSleepAt;
    }

    public void setWorstSleepAt(Date worstSleepAt) {
        this.worstSleepAt = worstSleepAt;
    }

    public Double getAverageSleepQuality() {
        return averageSleepQuality;
    }

    public void setAverageSleepQuality(Double averageSleepQuality) {
        this.averageSleepQuality = averageSleepQuality;
    }

    public List<Date> getSleepTimeList() {
        return sleepTimeList;
    }

    public void setSleepTimeList(List<Date> sleepTimeList) {
        this.sleepTimeList = sleepTimeList;
    }

    public List<Double> getSleepQualityList() {
        return sleepQualityList;
    }

    public void setSleepQualityList(List<Double> sleepQualityList) {
        this.sleepQualityList = sleepQualityList;
    }

    public Double getTotalSleepHours() {
        return totalSleepHours;
    }

    public void setTotalSleepHours(Double totalSleepHours) {
        this.totalSleepHours = totalSleepHours;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
