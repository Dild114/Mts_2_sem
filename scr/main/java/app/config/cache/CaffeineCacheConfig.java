package app.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CaffeineCacheConfig {

  @Bean
  public CacheManager cacheManager(Caffeine caffeine) {
    CaffeineCacheManager cacheManager = new
        CaffeineCacheManager("Sites");
    cacheManager.setCaffeine(caffeine);
    return cacheManager;
  }

  @Bean
  Caffeine caffeineSpec() {
    return Caffeine.newBuilder()
        .initialCapacity(10)
        .maximumSize(50)
        .expireAfterAccess(10, TimeUnit.MINUTES);
  }
}