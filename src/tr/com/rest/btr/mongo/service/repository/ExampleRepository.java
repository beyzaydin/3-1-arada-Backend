package tr.com.rest.btr.mongo.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tr.com.rest.btr.mongo.service.entity.ExampleEntity;

@Repository
public interface ExampleRepository extends MongoRepository<ExampleEntity, Long> {
}
