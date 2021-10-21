package ru.jegensomme.homeaccountant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Objects;

@Configuration
@EnableCaching
public class CacheConfig {
    @Value("classpath:cache/ehcache.xml")
    private Resource cacheManager;

    @Bean
    public CacheManager cacheManager() throws IOException {
        JCacheManagerFactoryBean cacheManagerFactoryBean = new JCacheManagerFactoryBean();
        cacheManagerFactoryBean.setCacheManagerUri(cacheManager.getURI());
        cacheManagerFactoryBean.afterPropertiesSet();
        return new JCacheCacheManager(Objects.requireNonNull(cacheManagerFactoryBean.getObject()));
    }
}
