package winx.bitirme.messaging.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import winx.bitirme.messaging.service.entity.ContactList;

public interface ContactListRepository extends MongoRepository<ContactList,Long> {
}
