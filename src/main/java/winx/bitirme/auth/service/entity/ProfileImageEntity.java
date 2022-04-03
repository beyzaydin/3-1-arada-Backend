package winx.bitirme.auth.service.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "profile-image")
public class ProfileImageEntity {
    @Transient
    public static final String SEQUENCE_NAME = "profil_image_sequence";

    @Id
    private long id;
    private String email;
    private byte[] profilePicture;
    private String type;

    public ProfileImageEntity(String email,
                              byte[] profilePicture,
                              String type) {
        this.email = email;
        this.profilePicture = profilePicture;
        this.type = type;
    }

    public ProfileImageEntity(long id,
                              String email,
                              byte[] profilePicture,
                              String type) {
        this.id = id;
        this.email = email;
        this.profilePicture = profilePicture;
        this.type = type;
    }

    public ProfileImageEntity() {
    }

    public long getId() {
        return id;
    }

    public ProfileImageEntity setId(long id) {
        this.id = id;
        return this;
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
        return new ProfileImageEntity(id, email, profilePicture, type);
    }
}
