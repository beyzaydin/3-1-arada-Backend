package winx.bitirme.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import winx.bitirme.achievement.service.logic.UserAchievementService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UserAchievementJob {
    private static final Logger log = LoggerFactory.getLogger(UserAchievementJob.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final UserAchievementService service;

    @Autowired
    public UserAchievementJob(UserAchievementService service) {
        this.service = service;
    }

    @Scheduled(cron = "0 0 0 * * *")
    //@Scheduled(cron = "*/1 * * * * * ")
    public void reportCurrentTime() {
        service.dailyAchievementRefresh();
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
