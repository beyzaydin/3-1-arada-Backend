package winx.bitirme.messaging.service.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document

public class MessagingHistory {

    @Id
    private MessagingHistoryId id;
    private long latestConversation;

    public MessagingHistoryId getId() {
        return id;
    }

    public void setId(MessagingHistoryId id) {
        this.id = id;
    }

    public long getLatestConversation() {
        return latestConversation;
    }

    public void setLatestConversation(long latestConversation) {
        this.latestConversation = latestConversation;
    }
}
