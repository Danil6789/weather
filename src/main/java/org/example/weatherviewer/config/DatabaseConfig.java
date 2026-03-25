package org.example.weatherviewer.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

    @Value("${spring.db.url}")
    private String dbUrl;

    @Value("${spring.db.username}")
    private String dbUsername;

    @Value("${spring.db.password}")
    private String dbPassword;

    @Bean
    @Profile("!test")
    public DataSource prodDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setJdbcUrl(dbUrl);
        ds.setUsername(dbUsername);
        ds.setPassword(dbPassword);
        ds.setMaximumPoolSize(10);
        return ds;
    }

    @Bean
    @Profile("test")
    public DataSource testDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setJdbcUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        ds.setUsername("sa");
        ds.setPassword("");
        ds.setMaximumPoolSize(2);
        return ds;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("org.example.weatherviewer.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    private Properties hibernateProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.hbm2ddl.auto", "validate");
        return props;
    }
}