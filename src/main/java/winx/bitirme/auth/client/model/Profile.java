package winx.bitirme.auth.client.model;

import winx.bitirme.auth.service.entity.EnumGender;
import winx.bitirme.auth.service.entity.Role;
import winx.bitirme.auth.service.entity.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Profile {
    private String username;

    private String name;

    private String surname;

    private EnumGender gender;

    private Date birthDate;

    private String email;

    private Set<Role> roles = new HashSet<>();

    private Integer sleepProgress;

    private Integer meditationProgress;

    private byte[] profilePicture;

    public void setUserInfo(User user) {
        setUsername(user.getUsername());
        setEmail(user.getEmail());
        setGender(user.getGender());
        setName(user.getName());
        setRoles(user.getRoles());
        setSurname(user.getSurname());
        setBirthDate(user.getBirthDate());
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnumGender getGender() {
        return gender;
    }

    public void setGender(EnumGender gender) {
        this.gender = gender;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
}
