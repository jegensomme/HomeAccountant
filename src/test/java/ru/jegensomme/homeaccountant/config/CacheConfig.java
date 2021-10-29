package ru.jegensomme.homeaccountant.config;

import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new NoOpCacheManager();
    }

    @Bean public PropertyOverrideConfigurer propertyOverrideConfigurer() {
        PropertyOverrideConfigurer overrideConfigurer = new PropertyOverrideConfigurer();
        overrideConfigurer.setLocation(new ClassPathResource("override.properties"));
        return overrideConfigurer;
    }
}
