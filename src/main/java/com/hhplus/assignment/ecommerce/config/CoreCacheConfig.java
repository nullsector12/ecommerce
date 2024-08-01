package com.hhplus.assignment.ecommerce.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.hhplus.assignment.ecommerce.common.model.ServerCache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableCaching
public class CoreCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Arrays.stream(ServerCache.values()).forEach(eCacheConfig ->
                cacheManager.registerCustomCache(eCacheConfig.getName(),
                        Caffeine.newBuilder()
                                .maximumSize(eCacheConfig.getMaximumSize())
                                .expireAfterWrite(Duration.ofSeconds(eCacheConfig.getExpireAfterWriteInSec()))
                                .build()));
        return cacheManager;
    }
}
