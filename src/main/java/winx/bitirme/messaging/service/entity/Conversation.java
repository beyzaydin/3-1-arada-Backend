package winx.bitirme.messaging.service.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class Conversation {


    @Id
    private ConversationId id;
    private boolean isClosed;
    public static long expireTime = 21600;//SECONDS
    private List<Message> conversation;
    public Conversation(ConversationId id){
        this.isClosed = false;
        this.conversation = new ArrayList<>();
        this.id = id;
    }
    public Message getLatestMessage(){
        return this.conversation.get(this.conversation.size()-1);
    }
    public void addMessage(Message toAdd){
        this.conversation.add(toAdd);
    }
}
