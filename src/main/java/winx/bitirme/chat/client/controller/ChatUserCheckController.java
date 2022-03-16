package winx.bitirme.chat.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import winx.bitirme.auth.service.repository.UserRepository;
import winx.bitirme.chat.service.logic.ChatService;
import winx.bitirme.chat.service.repository.ChatRoomRepository;

@RestController
@RequestMapping("/chat")
@CrossOrigin()
public class ChatUserCheckController {

    private final ChatRoomRepository repository;
    private final ChatService service;
    private final UserRepository userRepository;

    @Autowired
    public ChatUserCheckController(ChatRoomRepository repository,
                                   ChatService service, UserRepository userRepository) {
        this.repository = repository;
        this.service = service;
        this.userRepository = userRepository;
    }


    @GetMapping(value = "/connect/{username}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity checkUser(@PathVariable String username) throws InterruptedException {
        return service.check(username);
    }
}
 /*ExecutorService executor = Executors.newFixedThreadPool(1);
                AtomicReference<ChatRoom> chatRoom = null;
                Callable<String> callableTask = () -> {
                    while (!executor.isShutdown()) {
                        UserDetailsImpl res = service.handleWaitingUsers(user);
                        if (res == null)
                            continue;
                        else {
                            chatRoom.set(new ChatRoom());
                            chatRoom.get().setUser1(user.getUsername());
                            chatRoom.get().setUser2(res.getUsername());
                            chatRoom.get().setActive(true);
                        }
                    }
                    service.removeUser(user);
                    return null;
                };
                List<Callable<String>> callableTasks = new ArrayList<>();
                callableTasks.add(callableTask);
                List<Future<String>> futures = executor.invokeAll(callableTasks, 1, TimeUnit.MINUTES);

                executor.shutdown();
                try {
                    if (!executor.awaitTermination(100, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executor.shutdownNow();
                }
                if (chatRoom == null)
                    return ResponseEntity
                            .status(HttpStatus.REQUEST_TIMEOUT)
                            .body(new MessageResponse("Another user can not be found for chat."));

                return ResponseEntity.ok(chatRoom);*/