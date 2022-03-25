package winx.bitirme.auth.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.auth.service.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    User findByEmail(String name);

    User findByUsername(String username);
}
