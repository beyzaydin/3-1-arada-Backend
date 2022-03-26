package winx.bitirme.auth.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import winx.bitirme.auth.client.model.Profile;
import winx.bitirme.auth.service.entity.ProfileImageEntity;
import winx.bitirme.auth.service.entity.User;
import winx.bitirme.auth.service.repository.ProfileImageRepository;
import winx.bitirme.auth.service.repository.UserRepository;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {
    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;

    @Autowired
    public ProfileController(UserRepository userRepository, ProfileImageRepository profileImageRepository) {
        this.userRepository = userRepository;
        this.profileImageRepository = profileImageRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Profile profile() {
        Profile profile = new Profile();
        profile.setUserInfo(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        profile.setSleepProgress(-1);
        profile.setMeditationProgress(-1);
        return profile;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Profile updateProfile(@RequestBody Profile profile) {
        Profile prof = new Profile();
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setName(profile.getName());
        user.setSurname(profile.getSurname());
        user.setEmail(profile.getEmail());
        user.setBirthDate(profile.getBirthDate());
        user.setGender(profile.getGender());
        user.setRoles(profile.getRoles());
        user = userRepository.save(user);
        prof.setUserInfo(user);
        profile.setSleepProgress(-1);
        profile.setMeditationProgress(-1);
        return prof;
    }

    @PostMapping("/profile-pic")
    public ResponseEntity uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        profileImageRepository.save(
                new ProfileImageEntity().setEmail(email)
                        .setType(file.getContentType())
                        .setProfilePicture(file.getBytes()));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/profile-pic/update")
    public ResponseEntity updateImage(@RequestParam("image") MultipartFile file) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ProfileImageEntity profileImage = profileImageRepository.findByEmail(email);
        if(profileImage == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("This user has no profile picture. Therefore updating is not an option");
        profileImageRepository.save( profileImage.setProfilePicture(file.getBytes()));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/profile-pic")
    public ResponseEntity deleteImage() throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ProfileImageEntity profileImage = profileImageRepository.findByEmail(email);
        if(profileImage == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("This user has no profile picture. Therefore deleting is not an option");
        profileImageRepository.delete( profileImage);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/profile-pic")
    public ResponseEntity<byte[]> getImage() throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ProfileImageEntity profileImage = profileImageRepository.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(profileImage.getProfilePicture());
    }

}
