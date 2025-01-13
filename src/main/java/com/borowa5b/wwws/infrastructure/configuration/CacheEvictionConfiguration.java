package com.borowa5b.wwws.infrastructure.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class CacheEvictionConfiguration {

    @Autowired
    private final CacheManager cacheManager;

    @Scheduled(cron = "0 0 0 ? * *", zone = "UTC")
    public void evictCache() {
        cacheManager.getCacheNames()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }
}
