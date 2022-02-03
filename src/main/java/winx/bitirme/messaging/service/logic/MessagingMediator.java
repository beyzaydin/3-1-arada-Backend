package winx.bitirme.messaging.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import winx.bitirme.auth.service.entity.User;
import winx.bitirme.auth.service.repository.UserRepository;
import winx.bitirme.messaging.client.model.InitialHistoryResponse;
import winx.bitirme.messaging.service.entity.*;
import winx.bitirme.messaging.service.mapper.ContactListResponse;
import winx.bitirme.messaging.service.repository.ContactListRepository;
import winx.bitirme.messaging.service.repository.ConversationRepository;
import winx.bitirme.messaging.service.repository.MessagingHistoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MessagingMediator {
    private final ContactListRepository contactListRepository;
    private final ConversationRepository conversationRepository;
    private final MessagingHistoryRepository messagingHistoryRepository;
    private final UserRepository userRepository;
    private static final int characterLimit = 10;
    public MessagingMediator(ContactListRepository contactListRepository, ConversationRepository conversationRepository, MessagingHistoryRepository messagingHistoryRepository, UserRepository userRepository) {
        this.contactListRepository = contactListRepository;
        this.conversationRepository = conversationRepository;
        this.messagingHistoryRepository = messagingHistoryRepository;
        this.userRepository = userRepository;
    }

    public ContactListResponse getContacts(long userId){
        return new ContactListResponse(this.contactListRepository.findById(userId).get());
    }
    public void addContact(long userId,long toAdd){
        Optional<ContactList> owner = this.contactListRepository.findById(userId) ;
        if (owner.isPresent() && !owner.get().getContacts().contains(toAdd)){
            owner.get().getContacts().add(toAdd);
            this.contactListRepository.insert(owner.get());
        }
    }
    public void addMessage(Message toAdd){
        long participant1 = toAdd.getReceiver() > toAdd.getSender() ? toAdd.getReceiver() : toAdd.getSender();
        long participant2 = toAdd.getReceiver() > toAdd.getSender() ? toAdd.getSender() : toAdd.getReceiver();
        Optional<MessagingHistory> current = this.messagingHistoryRepository.findById(new MessagingHistoryId(participant1,participant2));
        if (current.isPresent()){
            Conversation latest  = this.conversationRepository.findById(new ConversationId(participant1,participant2,current.get().getLatestConversation())).get();
            if (latest.getLatestMessage().getTimestamp()+ 21600000 < System.currentTimeMillis()){
                latest.setClosed(true);
                this.conversationRepository.save(latest);
                Conversation replace = new Conversation(new ConversationId(participant1,participant2,latest.getId().getConversationNumber()+1));
                replace.addMessage(toAdd);
                this.conversationRepository.insert(replace);
            }
            else{
                latest.addMessage(toAdd);
                this.conversationRepository.save(latest);
            }
        }
        else {
            Conversation initial = new Conversation(new ConversationId(participant1,participant2,0));
            initial.addMessage(toAdd);
            this.conversationRepository.insert(initial);
        }
    }
    public User getUser(long id){
        Optional<User> result = this.userRepository.findById(id);
        return result.isPresent() ? result.get() :  null;
    }
    private ConversationSummary summary(Conversation toSum,long respondentId){
        String content = toSum.getLatestMessage().getContent();
        String visible = content.length() > 10 ? content.substring(0,characterLimit-2) + "..." : content;
        User respondent = this.userRepository.findById(respondentId).get();
        return new ConversationSummary(respondent,visible);

    }
    public List<ConversationSummary> constructConversationSummary(long userId){
        Optional<ContactList> potentialContacts = this.contactListRepository.findById(userId);
        List<ConversationSummary> result = new ArrayList<>();
        long participant1,participant2;
        if (potentialContacts.isPresent()){
            ContactList contacts = potentialContacts.get();
            for (long respondentId : contacts.getContacts()){
                participant1 = respondentId > userId ? respondentId : userId;
                participant2 = respondentId > userId ? userId : respondentId;
                result.add(this.summary(this.conversationRepository.findById(new ConversationId(participant1,participant2,this.messagingHistoryRepository.findById(new MessagingHistoryId(participant1,participant2)).get().getLatestConversation())).get(),respondentId));
            }
            return result;
        }
        else {
            return null;
        }
    }
    public Conversation getConversationHistory(long userId, long respondentId){
        long participant1 = userId > respondentId ? userId : respondentId;
        long participant2 = userId > respondentId ? respondentId : userId;
        Optional<MessagingHistory> opt = this.messagingHistoryRepository.findById(new MessagingHistoryId(participant1,participant2));
        if (opt.isPresent()){
            long latest = opt.get().getLatestConversation();
            return this.conversationRepository.findById(new ConversationId(participant1,participant2,latest)).get();
        }
        else return null;
    }
    public Conversation getConversationHistory(long userId, long respondentId, long conversationId){
        long participant1 = userId > respondentId ? userId : respondentId;
        long participant2 = userId > respondentId ? respondentId : userId;
        return this.conversationRepository.findById(new ConversationId(participant1,participant2,conversationId)).get();
    }

}
