package winx.bitirme.auth.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.auth.service.entity.ProfileImageEntity;

@Repository
public interface ProfileImageRepository extends MongoRepository<ProfileImageEntity, String> {
    ProfileImageEntity findByEmail(String email);
    Boolean existsByEmail(String email);
}
