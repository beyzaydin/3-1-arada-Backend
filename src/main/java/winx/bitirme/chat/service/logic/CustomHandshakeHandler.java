package winx.bitirme.chat.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import winx.bitirme.auth.service.utils.JwtUtils;
import winx.bitirme.chat.client.model.StompPrincipal;

import java.security.Principal;
import java.util.Map;

@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    private final JwtUtils jwtUtils;

    @Autowired
    public CustomHandshakeHandler(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected Principal determineUser(
            ServerHttpRequest request,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) {
        // Generate principal with UUID as name
        String cookie = ((ServletServerHttpRequest) request).getHeaders().get("cookie").get(0);
        int ind = (cookie.indexOf("="));
        String token = cookie.substring(ind + 1);
        return new StompPrincipal(jwtUtils.getUserNameFromJwtToken(token));
    }
}

