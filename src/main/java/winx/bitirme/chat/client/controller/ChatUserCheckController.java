package winx.bitirme.chat.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import winx.bitirme.chat.client.model.ChatReportModel;
import winx.bitirme.chat.service.logic.ChatReportService;
import winx.bitirme.chat.service.logic.ChatService;

import java.util.List;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatUserCheckController {

    private final ChatService service;
    private final ChatReportService chatReportService;


    @Autowired
    public ChatUserCheckController(ChatService service,
                                   ChatReportService chatReportService) {
        this.service = service;
        this.chatReportService = chatReportService;
    }


    @GetMapping(value = "/connect/{username}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity checkUser(@PathVariable String username) throws InterruptedException {
        return service.check(username);
    }

    @PostMapping(value = "/connect/abort",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity abortSession() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return service.removeSocketInfo(username);
    }

    @GetMapping(value = "/check-connection",
            produces = MediaType.APPLICATION_JSON_VALUE)
    Boolean checkConnection() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return service.checkDb(username);
    }

    @PostMapping(value = "/chat-report",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChatReportModel saveChatReport(@RequestBody ChatReportModel model) {
        return chatReportService.save(model);
    }

    @PostMapping(value = "/chat-report/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChatReportModel updateChatReport(@RequestBody ChatReportModel model) throws Exception {
        return chatReportService.update(model);
    }

    @DeleteMapping(value = "/chat-report",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteChatReport(@RequestBody ChatReportModel model) throws Exception {
        chatReportService.delete(model);
    }

    @GetMapping(value = "/chat-report/reported/{reported}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ChatReportModel> getListByReportedEmail(@PathVariable String reported) {
        return chatReportService.getListByReportedEmail(reported);
    }

    @GetMapping(value = "/chat-report/reporter/{reporter}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ChatReportModel> getListByReporterEmail(@PathVariable String reporter) {
        return chatReportService.getListByReporterEmail(reporter);
    }
}
