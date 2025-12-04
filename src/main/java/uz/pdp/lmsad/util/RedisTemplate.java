package uz.pdp.lmsad.util;


import org.springframework.stereotype.Component;

@Component
public class RedisTemplate<T, T1> {
    public ThreadLocal<String> opsForValue() {
        return null;
    }

    public void delete(T1 s) {

    }
}
