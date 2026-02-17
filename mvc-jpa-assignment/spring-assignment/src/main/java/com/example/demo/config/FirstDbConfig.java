package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jca.support.LocalConnectionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
		basePackages ="com.example.demo.dao.first",
		entityManagerFactoryRef ="firstEntityManager",
		transactionManagerRef ="firstTransactionManager"
)
public class FirstDbConfig {

	
	@Bean
	public DataSource firstDataSource() {
		return DataSourceBuilder.create()
				.driverClassName("org.postgresql.Driver")
             .url("jdbc:postgresql://localhost:5432/test")
             .username("postgres")
				.password("4747")
             .build();
	}

@Bean
  public LocalContainerEntityManagerFactoryBean firstEntityManager(){
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(firstDataSource());
      em.setPackagesToScan("com.example.demo.models");
      em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      return em;
  }

@Bean
public PlatformTransactionManager firstTransactionManager() {
  return new JpaTransactionManager(firstEntityManager().getObject());
}
	

}

