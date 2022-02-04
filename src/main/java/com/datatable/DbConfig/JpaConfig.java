package com.datatable.DbConfig;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/*
 * @author Sana Naveen
 * 
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.datatable"},entityManagerFactoryRef = "entityManagerFactory" )
public class JpaConfig {

	@Bean
	@Primary
	public HikariDataSource dataSource() {
		
		//Hikari data source connection
		
		HikariConfig hikariConfig = new HikariConfig();
		//hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
		hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/mockdata");
		hikariConfig.setUsername("root");
		hikariConfig.setPassword("root");
		hikariConfig.setMaximumPoolSize(20);
		return new HikariDataSource(hikariConfig);
	}
	
	// Entity Manager Factory is used to create and remove jpa
	@Bean
	@Primary
	public EntityManagerFactory entityManagerFactory() {
		
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
		hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
		
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		factoryBean.setPackagesToScan("com.datatable");
		factoryBean.setDataSource(dataSource());
		
		Properties properties = new Properties();
		properties.setProperty("hibernate.default_schema", "mockdata");
		//properties.setProperty("hibernate.hbm2ddl.auto", "create");
		
		factoryBean.setJpaProperties(properties);
		factoryBean.afterPropertiesSet();
		
		return factoryBean.getObject();
	}
	
	
}
