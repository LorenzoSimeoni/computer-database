package com.excilys.formation.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.excilys.formation.validator.Validator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class ConnectionDatabase {
	private Connection connection;
	private HikariDataSource ds;
	private HikariConfig config;
	private final static Logger LOGGER = LogManager.getLogger(Validator.class.getName());

	
	private ConnectionDatabase(){}
	
	private static ConnectionDatabase connectionHikariDatabase = new ConnectionDatabase();
	
	public static ConnectionDatabase getInstance() {
		return connectionHikariDatabase;
	}
	
	public DataSource getDataSource() {
	    if (config == null) {
	        
	        InputStream input = null;
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
	    }
	    return ds;
	}

	public Connection connect() {
	    if (connection == null) {
	        try {
	            connection = getDataSource().getConnection();
	        } catch ( SQLException e) {
	        	LOGGER.error("Connection to database failed", e);
	        }
	    }
	    return connection;
	}
	
	public void disconnect() {
	    if (connection != null) {
	        try {
	            connection.close();
	            connection = null;
	        } catch (SQLException e) {
	        	LOGGER.error("Disconnection to database failed", e);
	        }
	    }
	}
}
