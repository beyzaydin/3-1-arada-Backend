package winx.bitirme.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import winx.bitirme.auth.service.entity.PasswordChangeTokenEntity;
import winx.bitirme.auth.service.repository.PasswordChangeTokenRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class EmailTokenJob {
    private static final Logger log = LoggerFactory.getLogger(EmailTokenJob.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final PasswordChangeTokenRepository passwordChangeTokenRepository;

    @Autowired
    public EmailTokenJob(PasswordChangeTokenRepository passwordChangeTokenRepository) {
        this.passwordChangeTokenRepository = passwordChangeTokenRepository;
    }

    @Scheduled(cron = "*/50 * * * * * ")
    public void reportCurrentTime() {
        List<PasswordChangeTokenEntity> list = passwordChangeTokenRepository.findAllByExpireDateBefore(new Date());
        list.forEach(el -> {
            el.setExpired(true);
            passwordChangeTokenRepository.save(el);
        });
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
