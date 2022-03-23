package winx.bitirme.chat.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import winx.bitirme.auth.client.model.MessageResponse;
import winx.bitirme.auth.service.logic.UserDetailsImpl;
import winx.bitirme.auth.service.repository.UserRepository;
import winx.bitirme.chat.service.converter.ChatRoomMapper;
import winx.bitirme.chat.service.entity.ChatRoom;
import winx.bitirme.chat.service.repository.ChatRoomRepository;
import winx.bitirme.mongo.service.logic.SequenceGeneratorService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ChatService {
    private final ChatRoomRepository repository;
    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final AtomicReference<Queue<UserDetailsImpl>> waitingUsers;
    private final ChatRoomMapper mapper;

    @Autowired
    public ChatService(ChatRoomRepository repository,
                       UserRepository userRepository,
                       SequenceGeneratorService sequenceGeneratorService,
                       ChatRoomMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.mapper = mapper;
        Queue<UserDetailsImpl> queue = new LinkedList<>();
        this.waitingUsers = new AtomicReference<>(queue);
    }


    public UserDetailsImpl handleWaitingUsers(UserDetailsImpl user) {
        //todo kendini yollamasın
        if (waitingUsers.get().size() >= 1) {
            UserDetailsImpl pop = waitingUsers.get().poll();
            //if (pop != null) { NULL KONTROLU GEREKLI DEGIL ZATEN SIZE KNT YAPILDI
            if (pop != null && pop.getUsername().equals(user.getUsername())) {
                UserDetailsImpl tmp = waitingUsers.get().poll();
                waitingUsers.get().add(pop);
                pop = tmp;
            }
            //}
            return pop;
        } else {
            waitingUsers.get().add(user);
        }
        return null;
    }

    public ResponseEntity check(String username) throws InterruptedException {
        UserDetailsImpl user = UserDetailsImpl.build(userRepository.findByUsername(username));
        if (checkDb(username)) {
            return ResponseEntity
                    .status(HttpStatus.I_AM_A_TEAPOT)
                    .body("Chat already open in another browser.");
        }
        AtomicReference<ChatRoom> chatRoom = new AtomicReference<>(new ChatRoom());
        UserDetailsImpl rec = handleWaitingUsers(user);
        if (rec == null) {
            ExecutorService executor = Executors.newFixedThreadPool(2);
            Callable<String> callableTask = () -> {
                while (!executor.isShutdown()) {
                    if (checkDb(user.getUsername())) {
                        chatRoom.set(repository.findByUser2(user.getUsername()));
                        String sender, receiver;
                        sender = chatRoom.get().getUser1();
                        receiver = chatRoom.get().getUser2();
                        long id = sequenceGeneratorService.generateSequence(ChatRoom.SEQUENCE_NAME);
                        chatRoom.get().setId(id);
                        chatRoom.get().setUser1(receiver);
                        chatRoom.get().setUser2(sender);
                        repository.save(chatRoom.get());
                        removeUser(user);
                        executor.shutdown();
                        break;
                    }
                }
                return null;
            };
            List<Callable<String>> callableTasks = new ArrayList<>();
            callableTasks.add(callableTask);
            executor.invokeAll(callableTasks, 1, TimeUnit.MINUTES);

            executor.shutdown();
            //todo null pointer exception geliyor
            if (!chatRoom.get().getUser1().equals(user.getUsername())) {
                return ResponseEntity
                        .status(HttpStatus.REQUEST_TIMEOUT)
                        .body(new MessageResponse("Another user can not be found for chat."));
            }
            return ResponseEntity.ok(mapper.convertToModel(chatRoom.get()));
        }
        long id = sequenceGeneratorService.generateSequence(ChatRoom.SEQUENCE_NAME);
        chatRoom.get().setId(id);
        chatRoom.get().setUser1(user.getUsername());
        chatRoom.get().setUser2(rec.getUsername());
        chatRoom.get().setActive(true);
        repository.save(chatRoom.get());
        return ResponseEntity.ok(mapper.convertToModel(chatRoom.get()));
    }

    public Boolean removeUser(UserDetailsImpl user) {
        return waitingUsers.get().remove(user);
    }

    public void removeSocketInfo(UserDetailsImpl user) {
        ChatRoom user1 = repository.findByUser1(user.getUsername());
        ChatRoom user2 = repository.findByUser2(user.getUsername());
        if (user1 != null)
            repository.delete(user1);
        if (user2 != null)
            repository.delete(user2);
    }

    private Boolean checkDb(String username) {
        return (repository.existsByUser1(username) || repository.existsByUser2(username));
    }
}