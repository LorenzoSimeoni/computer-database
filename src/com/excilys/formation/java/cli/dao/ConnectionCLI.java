/**
 * 
 */
package com.excilys.formation.java.cli.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author excilys
 *
 */
public class ConnectionCLI {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/computer-database-db";
	
	private Connection connection;
	private Properties properties;
	
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
				// TODO: handle exception
			}finally {
				if (input != null) {
					try {
						input.close();
					} catch (final IOException e) {
						e.printStackTrace();
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
	            e.printStackTrace();
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
	            e.printStackTrace();
	        }
	    }
	}

}
