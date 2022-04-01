package winx.bitirme.auth.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.auth.service.entity.ToDoEntity;

import java.util.List;

@Repository
public interface ToDoRepository extends MongoRepository<ToDoEntity, Long> {
    List<ToDoEntity> findAllByUsername(String username);
    ToDoEntity findByUsernameAndTask(String username, String task);
    void deleteByUsernameAndTask(String username, String task);
}
