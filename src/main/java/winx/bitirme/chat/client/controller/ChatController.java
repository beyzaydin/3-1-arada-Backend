package winx.bitirme.chat.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import winx.bitirme.chat.client.model.Message;
import winx.bitirme.mongo.service.logic.SequenceGeneratorService;

@RestController
public class ChatController {

    @Transient
    public static final String SEQUENCE_NAME = "chat_message_sequence";
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate, SequenceGeneratorService sequenceGeneratorService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message) {
        Long id = sequenceGeneratorService.generateSequence(this.SEQUENCE_NAME);
        message.setId(id);
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message) {
        Long id = sequenceGeneratorService.generateSequence(this.SEQUENCE_NAME);
        message.setId(id);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        return message;
    }
}
