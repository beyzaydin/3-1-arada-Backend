package winx.bitirme.sleep.client.model;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class SleepStatisticModel {
    private Long id;
    @NotNull
    private Date date;
    @NotNull
    private Double frequency;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
