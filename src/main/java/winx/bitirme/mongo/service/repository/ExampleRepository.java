package winx.bitirme.mongo.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.mongo.service.entity.ExampleEntity;

@Repository
public interface ExampleRepository extends MongoRepository<ExampleEntity, Long> {
}
