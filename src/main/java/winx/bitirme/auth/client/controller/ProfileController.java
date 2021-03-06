package winx.bitirme.auth.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import winx.bitirme.auth.client.model.FormCompleteResponse;
import winx.bitirme.auth.client.model.Profile;
import winx.bitirme.auth.client.model.ToDoModel;
import winx.bitirme.auth.service.entity.ProfileImageEntity;
import winx.bitirme.auth.service.entity.User;
import winx.bitirme.auth.service.logic.ToDoService;
import winx.bitirme.auth.service.repository.ProfileImageRepository;
import winx.bitirme.auth.service.repository.UserRepository;
import winx.bitirme.mongo.service.logic.ClusteringFormService;
import winx.bitirme.mongo.service.logic.SequenceGeneratorService;

import java.io.IOException;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfileController {
    private final UserRepository userRepository;
    private final ProfileImageRepository profileImageRepository;
    private final ToDoService toDoService;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final ClusteringFormService clusteringFormService;
    @Autowired
    public ProfileController(UserRepository userRepository,
                             ProfileImageRepository profileImageRepository,
                             ToDoService toDoService, SequenceGeneratorService sequenceGeneratorService, ClusteringFormService clusteringFormService) {
        this.userRepository = userRepository;
        this.profileImageRepository = profileImageRepository;
        this.toDoService = toDoService;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.clusteringFormService = clusteringFormService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Profile profile() {
        Profile profile = new Profile();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        profile.setUserInfo(userRepository.findByEmail(username));
        if (profileImageRepository.existsByEmail(username))
            profile.setProfilePicture(profileImageRepository.findByEmail(username).getProfilePicture());
        profile.setSleepProgress(-1);
        profile.setMeditationProgress(-1);
        return profile;
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.IMAGE_JPEG_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Profile updateProfile(@RequestBody Profile profile) {
        Profile prof = new Profile();
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (profile.getName() != null)
            user.setName(profile.getName());
        if (profile.getSurname() != null)
            user.setSurname(profile.getSurname());
        if (profile.getEmail() != null)
            user.setEmail(profile.getEmail());
        if (profile.getBirthDate() != null)
            user.setBirthDate(profile.getBirthDate());
        if (profile.getGender() != null)
            user.setGender(profile.getGender());
        if (!profile.getRoles().isEmpty())
            user.setRoles(profile.getRoles());
        if (profile.getMeditationProgress() != null)
            prof.setMeditationProgress(profile.getMeditationProgress());
        if (profile.getSleepProgress() != null)
            prof.setSleepProgress(profile.getSleepProgress());
        if (profileImageRepository.existsByEmail(user.getEmail()))
            prof.setProfilePicture(profileImageRepository.findByEmail(user.getEmail()).getProfilePicture());
        user = userRepository.save(user);
        prof.setUserInfo(user);
        return prof;
    }

    @PostMapping("/profile-pic")
    public ResponseEntity uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ProfileImageEntity profileImage = profileImageRepository.findByEmail(email);
        if (profileImage != null)
            return updateImage(file);
        profileImageRepository.save(
                new ProfileImageEntity()
                        .setId(sequenceGeneratorService.generateSequence(ProfileImageEntity.SEQUENCE_NAME))
                        .setEmail(email)
                        .setType(file.getContentType())
                        .setProfilePicture(file.getBytes()));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/profile-pic/update")
    public ResponseEntity updateImage(@RequestParam("image") MultipartFile file) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ProfileImageEntity profileImage = profileImageRepository.findByEmail(email);
        if (profileImage == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("This user has no profile picture. Therefore updating is not an option");
        profileImageRepository.save(profileImage.setProfilePicture(file.getBytes()));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/profile-pic")
    public ResponseEntity deleteImage() throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ProfileImageEntity profileImage = profileImageRepository.findByEmail(email);
        if (profileImage == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("This user has no profile picture. Therefore deleting is not an option");
        profileImageRepository.delete(profileImage);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/profile-pic",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage() throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        ProfileImageEntity profileImage = profileImageRepository.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(profileImage.getProfilePicture());
    }

    @PostMapping(value = "/todo",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveToDoModel(@RequestBody ToDoModel model) {
        model.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.status(200).body(toDoService.saveToDoModel(model));
    }

    @PutMapping(value = "/todo",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateTask(@RequestBody ToDoModel model) {
        model.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ToDoModel fromDb = toDoService.updateTask(model);
        if (fromDb == null)
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("Model is not found!");
        return ResponseEntity.ok(fromDb);
    }
    @GetMapping(value = "/todo",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getToDoListByUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(toDoService.getToDoListByUsername(username));
    }
    @PostMapping(value ="/deleteTask", consumes=MediaType.APPLICATION_JSON_VALUE)
    public void deleteTask(@RequestBody ToDoModel model){
        model.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        toDoService.deleteTask(model.getTask(),model.getUsername());
    }
    @GetMapping(value="/isFormComplete", produces=MediaType.APPLICATION_JSON_VALUE)
    public FormCompleteResponse isFormComplete(){
        boolean isComplete = clusteringFormService.didUserCompleteForm(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return new FormCompleteResponse(isComplete);
    }
}
