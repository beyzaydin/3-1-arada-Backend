package winx.bitirme.messaging.service.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Message {
    private long sender;
    private long receiver;
    private String content;
    private long timestamp;
}
