package winx.bitirme.auth.service.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;

@Document(collection = "password-change-token")
public class PasswordChangeTokenEntity {
    @Transient
    public static final String SEQUENCE_NAME = "password_change_token_seq";
    @Id
    private Long id;
    private String email;
    private String token;
    private Boolean isExpired;
    private Date expireDate;

    public PasswordChangeTokenEntity() {
        setExpired(false);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, 2);
        this.expireDate = now.getTime();
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isExpired() {
        return (new Date().after(expireDate));
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

}
