package winx.bitirme.auth.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import winx.bitirme.auth.client.model.Profile;
import winx.bitirme.auth.service.entity.User;
import winx.bitirme.auth.service.repository.UserRepository;

@RestController
@RequestMapping("/profile")
@CrossOrigin
public class ProfileController {
    private final UserRepository userRepository;

    @Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public Profile updateProfile(@RequestBody Profile profile){
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
}
