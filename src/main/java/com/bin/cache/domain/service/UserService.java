package com.bin.cache.domain.service;

import com.bin.cache.domain.entity.RedisHashUser;
import com.bin.cache.domain.entity.User;
import com.bin.cache.domain.repository.RedisHashUserRepository;
import com.bin.cache.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.bin.cache.config.CacheConfig.CACHE1;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RedisTemplate<String, User> userRedisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;
    private final RedisHashUserRepository redisHashUserRepository;

    public User getUser(final Long id){
        var key = "user:%d".formatted(id);

        var cachedUser = objectRedisTemplate.opsForValue().get(key);
        if(cachedUser != null){
            return (User)cachedUser;
        }
        User user = userRepository.findById(id).orElseThrow();
        objectRedisTemplate.opsForValue().set(key, user, Duration.ofSeconds(30));
        return user;

        // 2. else db -> cache set

    }

    public RedisHashUser getUser2(final Long id){

        var cachedUser = redisHashUserRepository.findById(id).orElseGet(() -> {
            User user = userRepository.findById(id).orElseThrow();
            return redisHashUserRepository.save(RedisHashUser.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .createdAt(user.getCreateAt())
                    .updatedAt(user.getUpdatedAt())
                    .build());
        });
        return cachedUser;
    }

    @Cacheable(cacheNames = CACHE1, key = "'user:' + #id")
    public User getUser3(final Long id){
        return userRepository.findById(id).orElseThrow();
    }

}
