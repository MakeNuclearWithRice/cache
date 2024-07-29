package com.bin.cache.domain.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@RedisHash(value = "redishash-user", timeToLive = 30L) //TTL은 HashMap에만 적용 추가로 저장되는 Set에는 적용X
public class RedisHashUser {

    @Id
    private Long id;

    private String name;

    @Indexed
    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
