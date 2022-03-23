package winx.bitirme.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import winx.bitirme.auth.service.logic.UserDetailsImpl;
import winx.bitirme.auth.service.repository.UserRepository;
import winx.bitirme.chat.service.logic.ChatService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class SessionDisconnectListener implements ApplicationListener<SessionDisconnectEvent> {
    private final ChatService service;
    private final UserRepository userRepository;

    @Autowired
    public SessionDisconnectListener(ChatService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;
    }

  /*  @EventListener
    @Override
    public void onApplicationEvent(SessionDisconnectEvent applicationEvent) {
        StompHeaderAccessor headerAccessor= StompHeaderAccessor.wrap(applicationEvent.getMessage());
        String username=(String)headerAccessor.getUser().getName();
        service.removeSocketInfo(UserDetailsImpl.build(userRepository.findByUsername(username)));
    }*/

    @EventListener(SessionSubscribeEvent.class)
    public void onWebSocketSessionsConnected(SessionSubscribeEvent event) {
        Message<byte[]> eventMessage = event.getMessage();
        String token = getAuthorizationToken(eventMessage);
        // Bearer xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        // do whatever you need with user, throw exception if user should not be connected
        // ...
    }

    private String getAuthorizationToken(Message<byte[]> message) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        List<String> authorization = Optional.of(headerAccessor)
                .map($ -> $.getNativeHeader(WebSocketHttpHeaders.AUTHORIZATION))
                .orElse(Collections.emptyList());
        // if header does not exists returns null instead empty list :/

        return authorization.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Missing access token in Stomp message headers"));
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {


    }
}