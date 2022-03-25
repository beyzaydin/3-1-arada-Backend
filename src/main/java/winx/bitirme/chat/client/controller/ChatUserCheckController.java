package winx.bitirme.chat.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import winx.bitirme.chat.service.logic.ChatService;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping(value = "/connect/abort",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity abortSession(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return service.removeSocketInfo(username);
    }

    @GetMapping(value = "/check-connection",
    produces = MediaType.APPLICATION_JSON_VALUE)
    Boolean checkConnection(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return service.checkDb(username);
    }
}
