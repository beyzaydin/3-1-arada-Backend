package winx.bitirme.auth.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.auth.service.entity.PasswordChangeTokenEntity;

@Repository
public interface PasswordChangeTokenRepository extends MongoRepository<PasswordChangeTokenEntity, Long> {
    PasswordChangeTokenEntity findByToken(String token);
}
