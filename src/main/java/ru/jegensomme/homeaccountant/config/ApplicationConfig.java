package ru.jegensomme.homeaccountant.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceConfig.class)
@ComponentScan("ru.jegensomme.homeaccountant")
public class ApplicationConfig {
}
