package ru.jegensomme.homeaccountant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@EnableTransactionManagement
@Import(DatabaseConfig.class)
public class PersistenceConfig {
    private final DatabaseConfig databaseConfig;

    public PersistenceConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Value("${jpa.showSql}")
    private boolean showSql;
    @Value("${hibernate.format_sql}")
    private boolean formatSql;
    @Value("${hibernate.use_sql_comments}")
    private boolean useSqlComments;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(databaseConfig.dataSource());
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
                DIALECT, "org.hibernate.dialect.PostgreSQLDialect"
        ));
        return properties;
    }
}
