package ru.jegensomme.homeaccountant.config;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.postgresql.Driver;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public abstract class DatabaseConfig {
    @Bean
    public MethodInvokingBean methodInvokingBean() {
        MethodInvokingBean method = new MethodInvokingBean();
        method.setStaticMethod("org.slf4j.bridge.SLF4JBridgeHandler.install");
        return method;
    }

    @Configuration
    @Profile("postgres")
    @PropertySource(value={"classpath:db/postgres.properties"}, ignoreResourceNotFound = true)
    public static class PostgresConfig extends DatabaseConfig {
        @Value("${database.url}")
        private String url;
        @Value("${database.username}")
        private String username;
        @Value("${database.password}")
        private String password;

        @Value("classpath:db/initDB.sql")
        private Resource initDBScript;
        @Value("classpath:db/populateDB.sql")
        private Resource populateDBScript;
        @Value("${database.init}")
        private boolean initializerEnabled;

        @Bean
        public DataSource dataSource() {
            return new org.apache.tomcat.jdbc.pool.DataSource(getDataSourceProperties());
        }

        @Bean
        public DatabasePopulator populator() {
            return new ResourceDatabasePopulator(initDBScript, populateDBScript);
        }

        @Bean
        public DataSourceInitializer initializer() {
            DataSourceInitializer initializer = new DataSourceInitializer();
            initializer.setDataSource(dataSource());
            initializer.setDatabasePopulator(populator());
            initializer.setEnabled(initializerEnabled);
            return initializer;
        }

        private PoolProperties getDataSourceProperties() {
            PoolProperties properties = new PoolProperties();
            properties.setDriverClassName(Driver.class.getName());
            properties.setUrl(url);
            properties.setUsername(username);
            properties.setPassword(password);
            return properties;
        }
    }

    @Configuration
    @Profile("tomcat")
    @PropertySource(value={"classpath:db/tomcat.properties"}, ignoreResourceNotFound = true)
    public static class TomcatConfig extends DatabaseConfig {
        @Value("${jdbc.url}")
        private String jdbcUrl;

        @Bean
        public DataSource dataSource() {
            try {
                return (DataSource) new JndiTemplate().lookup(jdbcUrl);
            } catch (NamingException e) {
                throw new BeanInitializationException("Error while trying to initialize DataSource", e);
            }
        }
    }
}
