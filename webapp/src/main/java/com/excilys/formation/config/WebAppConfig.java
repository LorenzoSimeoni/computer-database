package com.excilys.formation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Import(value = {CoreConfig.class, BindingConfig.class})
@ComponentScan(basePackages = { "com.excilys.formation.controller" })
public class WebAppConfig {
	private final static Logger LOGGER = LogManager.getLogger(SpringRootConfig.class.getName());

}
