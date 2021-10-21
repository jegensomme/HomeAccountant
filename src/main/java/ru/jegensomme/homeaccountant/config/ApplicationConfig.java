package ru.jegensomme.homeaccountant.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({PersistenceConfig.class, CacheConfig.class})
@ComponentScan({
        "ru.jegensomme.homeaccountant.service",
        "ru.jegensomme.homeaccountant.repository.impl"
})
public class ApplicationConfig {
}
