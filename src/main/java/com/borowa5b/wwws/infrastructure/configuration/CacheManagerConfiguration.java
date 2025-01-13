package com.borowa5b.wwws.infrastructure.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@EnableCaching
@Configuration
public class CacheManagerConfiguration {

    @Bean
    public CacheManager cacheManager() {
        final var cacheManager = new SimpleCacheManager();
        final var postsCache = new ConcurrentMapCache("forecast");
        cacheManager.setCaches(List.of(postsCache));
        return cacheManager;
    }
}
