package winx.bitirme.chat.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import winx.bitirme.auth.client.model.MessageResponse;
import winx.bitirme.auth.service.logic.UserDetailsImpl;
import winx.bitirme.auth.service.repository.UserRepository;
import winx.bitirme.chat.client.model.ChatRoom;
import winx.bitirme.chat.service.repository.ChatRoomRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ChatService {
    private final ChatRoomRepository repository;
    private final UserRepository userRepository;
    private AtomicReference<Queue<UserDetailsImpl>> waitingUsers;
    private AtomicReference<>

    @Autowired
    public ChatService(ChatRoomRepository repository,
                       UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        Queue<UserDetailsImpl> queue = new LinkedList<UserDetailsImpl>();
        this.waitingUsers = new AtomicReference<>(queue);
    }

    public UserDetailsImpl handleWaitingUsers(UserDetailsImpl user) {
        //todo kendini yollamasın
        if (waitingUsers.get().size() >= 1) {
            UserDetailsImpl pop = waitingUsers.get().poll();
            if (pop != null){
                if (pop.getUsername().equals(user.getUsername())) {
                    UserDetailsImpl tmp = waitingUsers.get().poll();
                    waitingUsers.get().add(pop);
                    pop = tmp;
                }
            }
            return pop;
        } else {
            waitingUsers.get().add(user);
        }
        return null;
    }

    public ResponseEntity check(String username) throws InterruptedException {
        UserDetailsImpl user = UserDetailsImpl.build(userRepository.findByUsername(username));
        if (repository.findByUser1(user.getUsername()) == null && repository.findByUser2(user.getUsername()) == null) {
            UserDetailsImpl val = handleWaitingUsers(user);
            if (val == null) {

                ExecutorService executor = Executors.newFixedThreadPool(2);
                AtomicReference<ChatRoom> chatRoom = new AtomicReference<ChatRoom>(new ChatRoom());
                Callable<String> callableTask = () -> {
                    while (!executor.isShutdown()) {
                        UserDetailsImpl res = handleWaitingUsers(user);
                        if (res == null)
                            continue;
                        else {
                            //chatRoom.set(new ChatRoom());
                            chatRoom.get().setUser1(user.getUsername());
                            chatRoom.get().setUser2(res.getUsername());
                            chatRoom.get().setActive(true);
                            executor.shutdown();
                        }
                    }
                    removeUser(user);
                    return null;
                };
                List<Callable<String>> callableTasks = new ArrayList<>();
                callableTasks.add(callableTask);
                List<Future<String>> futures = executor.invokeAll(callableTasks, 1, TimeUnit.MINUTES);

                executor.shutdown();
                /*try {
                    if (!executor.awaitTermination(100, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executor.shutdownNow();
                }*/
                if (chatRoom.get().getUser1() == null)
                    return ResponseEntity
                            .status(HttpStatus.REQUEST_TIMEOUT)
                            .body(new MessageResponse("Another user can not be found for chat."));

                return ResponseEntity.ok(chatRoom);
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

    public Boolean removeUser(UserDetailsImpl user) {
        return waitingUsers.get().remove(user);
    }
}



        /*boolean flag = false;
                long startTime = System.currentTimeMillis(); //fetch starting time
                AtomicReference<ChatRoom> chatRoom = new AtomicReference<ChatRoom>(new ChatRoom());
                while (false || (System.currentTimeMillis() - startTime) < 10000) {
                    UserDetailsImpl res = handleWaitingUsers(user);
                    if (res == null)
                        continue;
                    else {
                        flag = true;
                        chatRoom.get().setUser1(user.getUsername());
                        chatRoom.get().setUser2(res.getUsername());
                        chatRoom.get().setActive(true);
                        break;
                    }
                }

                if (!flag) {
                    removeUser(user);
                    return ResponseEntity
                            .status(HttpStatus.REQUEST_TIMEOUT)
                            .body(new MessageResponse("Another user can not be found for chat."));
                }
                return ResponseEntity.ok(chatRoom);*/