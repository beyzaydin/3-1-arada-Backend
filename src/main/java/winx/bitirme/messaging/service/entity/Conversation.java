package winx.bitirme.messaging.service.entity;


import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Conversation {


    @Id
    private ConversationId id;
    private boolean isClosed;
    public static long expireTime = 21600;//SECONDS
    private List<Message> conversation;

    public Conversation() {
    }

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

    public ConversationId getId() {
        return id;
    }

    public void setId(ConversationId id) {
        this.id = id;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public static long getExpireTime() {
        return expireTime;
    }

    public static void setExpireTime(long expireTime) {
        Conversation.expireTime = expireTime;
    }

    public List<Message> getConversation() {
        return conversation;
    }

    public void setConversation(List<Message> conversation) {
        this.conversation = conversation;
    }
}
