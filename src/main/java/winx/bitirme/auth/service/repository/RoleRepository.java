package main.java.winx.bitirme.auth.service.repository;

import main.java.winx.bitirme.auth.service.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, Long> {
}
