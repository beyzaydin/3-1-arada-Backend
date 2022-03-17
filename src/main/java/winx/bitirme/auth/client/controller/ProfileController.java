package winx.bitirme.auth.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import winx.bitirme.auth.client.model.Profile;
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
}
