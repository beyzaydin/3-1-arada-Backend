package winx.bitirme.mongo.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import winx.bitirme.mongo.service.entity.ClusteringForm;

public interface ClusteringFormRepository extends MongoRepository<ClusteringForm,Integer> {
}
