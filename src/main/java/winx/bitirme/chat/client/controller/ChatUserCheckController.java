package winx.bitirme.chat.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import winx.bitirme.chat.service.logic.ChatService;

@RestController
@RequestMapping("/chat")
@CrossOrigin()
public class ChatUserCheckController {

    private final ChatService service;


    @Autowired
    public ChatUserCheckController(ChatService service) {
        this.service = service;
    }


    @GetMapping(value = "/connect/{username}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity checkUser(@PathVariable String username) throws InterruptedException {
        return service.check(username);
    }


}
