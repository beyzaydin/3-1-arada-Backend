package winx.bitirme.mongo.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.mongo.service.entity.Answer;
@Repository
public interface AnswerRepository extends MongoRepository<Answer,String> {
}
