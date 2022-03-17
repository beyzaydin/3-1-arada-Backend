package winx.bitirme.config;

import org.apache.camel.spi.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import winx.bitirme.auth.service.logic.UserDetailsImpl;
import winx.bitirme.auth.service.repository.UserRepository;
import winx.bitirme.chat.service.logic.ChatService;

@Component("sessionDisconnectListener")

public class SessionDisconnectListener implements ApplicationListener<SessionDisconnectEvent> {
    private final ChatService service;
    private final UserRepository userRepository;

    @Autowired
    public SessionDisconnectListener(ChatService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

    @EventListener
    @Override
    public void onApplicationEvent(SessionDisconnectEvent applicationEvent) {
        StompHeaderAccessor headerAccessor= StompHeaderAccessor.wrap(applicationEvent.getMessage());

        String username=(String)headerAccessor.getSessionAttributes().get("username");
        service.removeUser(UserDetailsImpl.build(userRepository.findByUsername(applicationEvent.getUser().getName())));
    }
}