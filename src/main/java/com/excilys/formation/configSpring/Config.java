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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.excilys.formation.mapper.MapperRawCompany;
import com.excilys.formation.mapper.MapperRawComputer;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = { "com.excilys.formation.mapper", "com.excilys.formation.dao",
		"com.excilys.formation.cli", "com.excilys.formation.service", "com.excilys.formation.controller",
		"com.excilys.formation.validator" })
public class Config {
	private final static Logger LOGGER = LogManager.getLogger(Config.class.getName());

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

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}
	
	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		return namedParameterJdbcTemplate;
	}
	
	@Bean
	public RowMapper<Company> rowMapperCompanyTemplate(){
		RowMapper<Company> rowMapper = new MapperRawCompany();	
		return 	rowMapper;
	}
	
	@Bean
	public RowMapper<Computer> rowMapperComputerTemplate(){
		RowMapper<Computer> rowMapper = new MapperRawComputer();	
		return 	rowMapper;
	}
	
	@Bean
	public MapSqlParameterSource mapSqlParameterSource(){
		MapSqlParameterSource params = new MapSqlParameterSource();
		return params;
	}


}
