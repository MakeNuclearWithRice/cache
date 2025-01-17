package com.bin.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class CacheApplication implements ApplicationRunner {


    public static void main(String[] args) {
        SpringApplication.run(CacheApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
