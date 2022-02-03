package winx.bitirme.messaging.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import winx.bitirme.messaging.service.entity.MessagingHistory;
import winx.bitirme.messaging.service.entity.MessagingHistoryId;

public interface MessagingHistoryRepository extends MongoRepository<MessagingHistory, MessagingHistoryId> {
}
