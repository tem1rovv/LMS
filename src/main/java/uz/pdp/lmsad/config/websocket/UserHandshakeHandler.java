package uz.pdp.lmsad.config.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

public class UserHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        Object p = attributes.get("principal");
        if (p instanceof Principal) return (Principal) p;
// fallback: if username present
        Object username = attributes.get("username");
        if (username != null) {
            return () -> String.valueOf(username);
        }
        return super.determineUser(request, wsHandler, attributes);
    }
}
