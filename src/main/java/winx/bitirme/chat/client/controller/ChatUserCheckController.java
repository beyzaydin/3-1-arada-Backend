package winx.bitirme.chat.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import winx.bitirme.auth.client.model.MessageResponse;
import winx.bitirme.auth.service.logic.UserDetailsImpl;
import winx.bitirme.chat.client.model.ChatRoom;
import winx.bitirme.chat.service.logic.ChatService;
import winx.bitirme.chat.service.repository.ChatRoomRepository;

@RestController
@RequestMapping("/chat/connect")
@CrossOrigin
public class ChatUserCheckController {

    private final ChatRoomRepository repository;
    private final ChatService service;

    @Autowired
    public ChatUserCheckController(ChatRoomRepository repository,
                                   ChatService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity checkUser() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (repository.findByUser1(user.getUsername()) == null && repository.findByUser2(user.getUsername()) == null) {
            UserDetailsImpl val = service.handleWaitingUsers();
            if (val == null) {
                //todo timeout 1dk
                //todo thread
                //todo threat safe hale getir
                return ResponseEntity
                        .status(HttpStatus.REQUEST_TIMEOUT)
                        .body(new MessageResponse("Another user can not be found for chat."));
            } else {
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setUser1(user.getUsername());
                chatRoom.setUser2(val.getUsername());
                chatRoom.setActive(true);
                return ResponseEntity.ok(chatRoom);
            }
        }
        return ResponseEntity
                .status(HttpStatus.I_AM_A_TEAPOT)
                .body("Chat already open in another browser.");
    }
}
