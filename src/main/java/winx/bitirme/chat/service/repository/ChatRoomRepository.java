package winx.bitirme.chat.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.chat.client.model.ChatRoom;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, Long> {
    ChatRoom findByUser1(String username);
    ChatRoom findByUser2(String username);
}
