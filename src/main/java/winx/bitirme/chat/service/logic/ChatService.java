package winx.bitirme.chat.service.logic;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import winx.bitirme.auth.service.logic.UserDetailsImpl;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class ChatService {
    private Queue<UserDetailsImpl> waitingUsers;

    public ChatService() {
        this.waitingUsers = new LinkedList<UserDetailsImpl>();
    }

    public UserDetailsImpl handleWaitingUsers() {
        if (waitingUsers.size() >= 1) {
            waitingUsers.poll();
        }else{
            waitingUsers.add((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        return null;
    }
}
