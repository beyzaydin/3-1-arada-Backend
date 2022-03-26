package winx.bitirme.auth.service.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "profile-image")
public class ProfileImageEntity {
    @Id
    private String email;
    private byte[] profilePicture;
    private String type;

    public ProfileImageEntity(String email, byte[] profilePicture, String type) {
        this.email = email;
        this.profilePicture = profilePicture;
        this.type = type;
    }

    public ProfileImageEntity() {
    }

    public String getEmail() {
        return email;
    }

    public ProfileImageEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public ProfileImageEntity setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public String getType() {
        return type;
    }

    public ProfileImageEntity setType(String type) {
        this.type = type;
        return this;
    }

    public ProfileImageEntity createProfileImageEntity() {
        return new ProfileImageEntity(email, profilePicture, type);
    }
}
