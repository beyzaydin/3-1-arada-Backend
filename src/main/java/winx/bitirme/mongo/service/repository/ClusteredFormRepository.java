package winx.bitirme.mongo.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import winx.bitirme.mongo.service.entity.ClusteredForm;

public interface ClusteredFormRepository extends MongoRepository<ClusteredForm,Integer> {
}
