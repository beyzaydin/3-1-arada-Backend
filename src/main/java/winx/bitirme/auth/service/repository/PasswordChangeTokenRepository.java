package winx.bitirme.auth.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.auth.service.entity.PasswordChangeTokenEntity;

import java.util.Date;
import java.util.List;

@Repository
public interface PasswordChangeTokenRepository extends MongoRepository<PasswordChangeTokenEntity, Long> {
    PasswordChangeTokenEntity findByToken(String token);
    List<PasswordChangeTokenEntity> findAllByExpireDateBefore(Date date);
}
