package winx.bitirme.sleep.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "sleep-statistic")
public class SleepStatisticEntity {
    private static final String SEQUENCE_NAME = "sleep-statistic";
    @Id
    private Long id;
    @NotNull
    private Date date;
    @NotNull
    private Double frequency;
    private String email;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
