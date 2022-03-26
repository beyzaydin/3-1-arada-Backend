package winx.bitirme.mongo.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.mongo.service.entity.ClusteredForm;
@Repository
public interface ClusteredFormRepository extends MongoRepository<ClusteredForm,Integer> {
}
