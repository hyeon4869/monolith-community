package community.community.config;

import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public org.springframework.cache.CacheManager conCacheManager() {
        return new ConcurrentMapCacheManager("myC");
    }
}
