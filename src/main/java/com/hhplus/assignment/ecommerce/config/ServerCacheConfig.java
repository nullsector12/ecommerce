package com.hhplus.assignment.ecommerce.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.hhplus.assignment.ecommerce.common.model.ServerCache;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class ServerCacheConfig {

    private final CacheManager cacheManager;

    @PostConstruct
    public void addCache() {
        if (cacheManager instanceof CaffeineCacheManager caffeineCacheManager) {
            Stream.of(ServerCache.values()).forEach(eCacheConfig ->
                    caffeineCacheManager.registerCustomCache(eCacheConfig.getName(),
                            Caffeine.newBuilder()
                                    .maximumSize(eCacheConfig.getMaximumSize())
                                    .expireAfterWrite(Duration.ofSeconds(eCacheConfig.getExpireAfterWriteInSec()))
                                    .build()));
        }
    }
}
