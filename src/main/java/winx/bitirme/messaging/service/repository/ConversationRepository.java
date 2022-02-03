package winx.bitirme.messaging.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import winx.bitirme.messaging.service.entity.Conversation;
import winx.bitirme.messaging.service.entity.ConversationId;

public interface ConversationRepository extends MongoRepository<Conversation, ConversationId> {
}
