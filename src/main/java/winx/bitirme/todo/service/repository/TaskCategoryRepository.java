package winx.bitirme.todo.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.todo.service.entity.TaskCategory;

@Repository
public interface TaskCategoryRepository extends MongoRepository<TaskCategory,Integer> {
}
