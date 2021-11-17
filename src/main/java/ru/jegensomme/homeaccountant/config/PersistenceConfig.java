package ru.jegensomme.homeaccountant.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.jegensomme.homeaccountant.web.View;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "ru.jegensomme.homeaccountant.repository.impl" })
@Import(DatabaseConfig.class)
@RequiredArgsConstructor
public class PersistenceConfig {
    private final DataSource dataSource;

    @Value("${jpa.showSql}")
    private boolean showSql;
    @Value("${hibernate.format_sql}")
    private boolean formatSql;
    @Value("${hibernate.use_sql_comments}")
    private boolean useSqlComments;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan("ru.jegensomme.homeaccountant.model");
        factory.setJpaProperties(getJpaProperties());
        factory.setJpaVendorAdapter(jpaVendorAdapter());
        return factory;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(showSql);
        return vendorAdapter;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory().getObject()));
    }

    private Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.putAll(Map.of(
                FORMAT_SQL, formatSql,
                USE_SQL_COMMENTS, useSqlComments,
                JPA_PROXY_COMPLIANCE, false,
                DIALECT, "org.hibernate.dialect.PostgreSQLDialect",
                USE_SECOND_LEVEL_CACHE, true,
                CACHE_REGION_FACTORY, "org.hibernate.cache.jcache.JCacheRegionFactory",
                CACHE_PROVIDER_CONFIG, "cache/ehcache.xml",
                JPA_UPDATE_VALIDATION_GROUP, View.Persist.class.getName(),
                JPA_PERSIST_VALIDATION_GROUP, View.Persist.class.getName()
        ));
        return properties;
    }
}
