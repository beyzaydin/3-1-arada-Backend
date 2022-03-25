package winx.bitirme.chat.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.chat.service.entity.ChatReportEntity;

@Repository
public interface ChatReportRepository extends MongoRepository<ChatReportEntity, Long> {
}
