package winx.bitirme.auth.client.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import winx.bitirme.auth.client.model.Profile;
import winx.bitirme.auth.service.logic.UserDetailsImpl;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Profile profile() {
        Profile profile = new Profile();
        profile.setUserDetails((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        profile.setSleepProgress(-1);
        profile.setMeditationProgress(-1);
        return profile;
    }
}
