package uz.pdp.lmsad.config.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import uz.pdp.lmsad.props.AppProps;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {


    private final AppProps appProps;

    public JwtTokenUtil(AppProps appProps) {
        this.appProps = appProps;
    }

    public String generateAccessToken(@NonNull String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + appProps.getAccessTokenExpireTime()))
                .signWith(signKey())
                .compact();
    }


    public String generateRefreshToken(@NonNull String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + appProps.getRefreshTokenExpireTime()))
                .signWith(signKey())
                .compact();
    }

    public boolean isValid(@NonNull String token) {
        try {
            Claims claims = getClaims(token);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsername(@NonNull String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(signKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key signKey() {
        byte[] bytes = Decoders.BASE64.decode(appProps.getSecretKey());
        return Keys.hmacShaKeyFor(bytes);
    }
}
