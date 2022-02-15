package winx.bitirme.messaging.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import winx.bitirme.messaging.service.entity.Conversation;
import winx.bitirme.messaging.service.entity.ConversationSummary;
import winx.bitirme.messaging.service.entity.Message;
import winx.bitirme.messaging.service.logic.MessagingMediator;
import winx.bitirme.messaging.service.mapper.ContactListResponse;

import java.util.List;

@Controller
@RequestMapping(value = "/messages")
public class MessagingController {
    final MessagingMediator messagingMediator;

    public MessagingController(MessagingMediator messagingService) {
        this.messagingMediator = messagingService;
    }

    public ContactListResponse getContacts(long userId){
        return this.messagingMediator.getContacts(userId);
    }
    @RequestMapping(path="/initialHistory")
    public List<ConversationSummary> getGeneralConversationHistory(long user, long respondent){
       return this.messagingMediator.constructConversationSummary(user);
    }
    @RequestMapping(method = RequestMethod.POST, path="/get-history", consumes="application/json", produces = "application/json")
    public Conversation getConversation(long userId, long respondentId){
        return this.messagingMediator.getConversationHistory(userId,respondentId);
    }
    @RequestMapping(method = RequestMethod.POST, path="/getHistory", consumes="application/json", produces = "application/json")
    public Conversation getConversation(long userId, long respondentId,long conversationId){
        return this.messagingMediator.getConversationHistory(userId,respondentId,conversationId);
    }
    @RequestMapping(method = RequestMethod.POST, path="/sendMessage",consumes="application/json")
    public void sendMessage(Message toSend){
        this.messagingMediator.addMessage(toSend);
    }

}
