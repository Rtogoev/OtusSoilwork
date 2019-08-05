package ru.otus.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.cache.Cache;
import ru.otus.homework.cache.CacheImpl;
import ru.otus.homework.model.User;

@Configuration
public class CacheConfig {
    @Bean
    public Cache getCache() {
        return new CacheImpl<Long, User>();
    }
}
