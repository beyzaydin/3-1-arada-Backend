package winx.bitirme.messaging.service.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
public class ContactList {
    @Id
    private long userId;
    private List<Long> contacts;
}
