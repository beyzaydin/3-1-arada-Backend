package winx.bitirme.messaging.service.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
public class MessagingHistory {
    @Id
    private MessagingHistoryId id;
    private long latestConversation;
}
