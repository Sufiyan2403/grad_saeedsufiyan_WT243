package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.demo.dao.second",
        entityManagerFactoryRef = "secondEntityManager",
        transactionManagerRef = "secondTransactionManager"
)


public class SecondDbConfig {

    @Bean
    public DataSource secondDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/test2")
                .username("postgres")
                .password("4747")
                .build();
    }
    
    

    @Bean
    public LocalContainerEntityManagerFactoryBean secondEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(secondDataSource());
        em.setPackagesToScan("com.example.demo.models");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    
    @Bean
    public PlatformTransactionManager secondTransactionManager() {
        return new JpaTransactionManager(secondEntityManager().getObject());
    }
}
