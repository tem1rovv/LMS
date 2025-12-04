package uz.pdp.lmsad.props;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppProps {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.access.token.expiration}")
    private Long accessTokenExpireTime;

    @Value("${jwt.refresh.token.expiration}")
    private Long refreshTokenExpireTime;

    @Value("${telegram.token}")
    private String botToken;
    @Value("${telegram.chat_id}")
    private String chatId;

    @Value("${app.openai.url}")
    private String openaiUrl;

    @Value("${app.openai.apiKey}")
    private String openaiApiKey;


    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private String redisPort;
}
