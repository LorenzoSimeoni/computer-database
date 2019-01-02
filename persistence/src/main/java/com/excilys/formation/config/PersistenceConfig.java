package com.excilys.formation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Import(value = {CoreConfig.class})
@ComponentScan(basePackages = { "com.excilys.formation.dao"})
public class PersistenceConfig {

	@Bean
	public DataSource mysqlDataSource() {
		InputStream input = null;
		HikariDataSource ds = null;
		HikariConfig config = null;
//		try {
//			input = ClassLoader.getSystemClassLoader().getResourceAsStream("hikari.properties");
//			Properties properties = new Properties();
//			properties.load(input);
			config = new HikariConfig();
			config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/computer-database-db");
			config.setUsername("admincdb");
			config.setPassword("qwerty1234");
			config.setDriverClassName("com.mysql.jdbc.Driver");
//		} catch (IOException e) {
//			LOGGER.info("FILES NOT FOUND", e);
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (final IOException e) {
//					LOGGER.error("File db.properties fails to close", e);
//				}
//			}
//		}
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
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean session = new LocalSessionFactoryBean();
		session.setDataSource(dataSource);
		session.setHibernateProperties(hibernateProperties());
		session.setPackagesToScan("com.excilys.formation.model");
		return session;
	}
}
