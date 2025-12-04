package uz.pdp.lmsad.service;

import org.springframework.stereotype.Service;
import uz.pdp.lmsad.util.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveCode(String email, String code) {
//        redisTemplate.opsForValue().set("EMAIL_CODE:" + email, code, 2, TimeUnit.MINUTES);
    }

    public String getCode(String email) {
//        return redisTemplate.opsForValue().get("EMAIL_CODE:" + email);
        return null;
    }

    public void deleteCode(String email) {
        redisTemplate.delete("EMAIL_CODE:" + email);
    }
    // todo all code
}
