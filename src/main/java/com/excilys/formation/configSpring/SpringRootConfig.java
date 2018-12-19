package com.excilys.formation.configSpring;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = { "com.excilys.formation.mapper", "com.excilys.formation.dao",
		"com.excilys.formation.cli", "com.excilys.formation.service",
		"com.excilys.formation.validator" })
public class SpringRootConfig {
	private final static Logger LOGGER = LogManager.getLogger(SpringRootConfig.class.getName());

	@Bean
	public DataSource mysqlDataSource() {
		InputStream input = null;
		HikariDataSource ds = null;
		HikariConfig config = null;
		try {
			input = ClassLoader.getSystemClassLoader().getResourceAsStream("hikari.properties");
			Properties properties = new Properties();
			properties.load(input);
			config = new HikariConfig(properties);
		} catch (IOException e) {
			LOGGER.info("FILES NOT FOUND", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (final IOException e) {
					LOGGER.error("File db.properties fails to close", e);
				}
			}
		}
		ds = new HikariDataSource(config);
		return ds;
	}
	 
	Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.setProperty("hibernate.show_sql", "true");
		return properties;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource)
	{
		LocalSessionFactoryBean session = new LocalSessionFactoryBean();
		session.setDataSource(dataSource);
		session.setHibernateProperties(hibernateProperties());        
		session.setPackagesToScan(new String[]{"com.excilys.formation.model"});
	    return session;
	}
}
