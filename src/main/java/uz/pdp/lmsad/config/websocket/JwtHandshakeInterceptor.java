package uz.pdp.lmsad.config.websocket;


import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.server.HandshakeInterceptor;
import uz.pdp.lmsad.config.jwt.JwtTokenUtil;
import uz.pdp.lmsad.props.AppProps;


import java.security.Principal;
import java.util.List;
import java.util.Map;


public class JwtHandshakeInterceptor implements HandshakeInterceptor {


private JwtTokenUtil jwtUtil = new JwtTokenUtil(new AppProps());

@Override
public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
org.springframework.web.socket.WebSocketHandler wsHandler,
Map<String, Object> attributes) throws Exception {
String token = null;
List<String> auth = request.getHeaders().get("Authorization");
if (auth != null && !auth.isEmpty()) {
token = auth.get(0).replace("Bearer ", "");
} else if (request instanceof ServletServerHttpRequest servletRequest) {
String t = servletRequest.getServletRequest().getParameter("token");
if (t != null) token = t;
}


if (token == null || !jwtUtil.isValid(token)) {
return false; // reject handshake
}


String username = jwtUtil.getUsername(token);
attributes.put("username", username);
Principal user = () -> username; // simple principal
attributes.put("principal", user);
return true;
}


@Override
public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
org.springframework.web.socket.WebSocketHandler wsHandler, Exception exception) {
}
}