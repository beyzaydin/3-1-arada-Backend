package winx.bitirme.achievement.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import winx.bitirme.achievement.client.model.UserAchievementModel;
import winx.bitirme.achievement.service.logic.UserAchievementService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/achievement")
public class UserAchievementController {
    private final UserAchievementService service;

    @Autowired
    public UserAchievementController(UserAchievementService service) {
        this.service = service;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody UserAchievementModel model) {
        return service.update(model);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getList() {
        return service.getListByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
