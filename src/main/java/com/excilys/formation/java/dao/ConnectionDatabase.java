/**
 * 
 */
package com.excilys.formation.java.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.java.validator.Validator;

/**
 * @author excilys
 *
 */
public class ConnectionDatabase {
	private final static Logger LOGGER = LogManager.getLogger(Validator.class.getName());
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/computer-database-db";
	
	private Connection connection;
	private Properties properties;
	
	private ConnectionDatabase(){}
	
	private static ConnectionDatabase connectionDatabase = new ConnectionDatabase();
	
	public static ConnectionDatabase getInstance() {
		return connectionDatabase;
	}
	
	/**
	 * Get the properties of the database connection
	 * @return an object Properties containing URL, user and mdp
	 */
	private Properties getProperties() {
	    if (properties == null) {
	        properties = new Properties();
	        InputStream input = null;
	        try {
	        	input = new FileInputStream("db/db.properties");
	        	properties.load(input);
			} catch (IOException e) {
				LOGGER.info("FILES NOT FOUND", e);
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (final IOException e) {
						LOGGER.info("File db.properties fails to close", e);
					}
				}	
			}
	    }
	    return properties;
	}
	
	// connect database
	public Connection connect() {
	    if (connection == null) {
	        try {
	            Class.forName(DB_DRIVER);
	            connection = DriverManager.getConnection(URL, getProperties());
	        } catch (ClassNotFoundException | SQLException e) {
	        	LOGGER.info("Connection to database failed", e);
	        }
	    }
	    return connection;
	}
	
	// disconnect database
	public void disconnect() {
	    if (connection != null) {
	        try {
	            connection.close();
	            connection = null;
	        } catch (SQLException e) {
	        	LOGGER.info("Disconnection to database failed", e);
	        }
	    }
	}

}
