package winx.bitirme.auth.service.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.auth.service.entity.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {
}
