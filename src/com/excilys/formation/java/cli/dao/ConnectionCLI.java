/**
 * 
 */
package com.excilys.formation.java.cli.dao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;



/**
 * @author excilys
 *
 */
public class ConnectionCLI {
	
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/computer-database-db";
	private static final String USER = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	private static final String MAX_POOL = "250";
	
	private Connection connection;
	private Properties properties;
	
	// create properties
	private Properties getProperties() {
	    if (properties == null) {
	        properties = new Properties();
	        properties.setProperty("user", USER);
	        properties.setProperty("password", PASSWORD);
	        properties.setProperty("MaxPooledStatements", MAX_POOL);
	    }
	    return properties;
	}
	
	// connect database
	public Connection connect() {
	    if (connection == null) {
	        try {
	            Class.forName(DB_DRIVER);
	            System.out.println("Driver O.K.");
	            System.out.println("Connecting database ...");
	            connection = DriverManager.getConnection(URL, getProperties());
	            System.out.println("Database connected !");
	        } catch (ClassNotFoundException | SQLException e) {
	            // Java 7+
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
	            System.out.println("Database disconnected !");
	            connection = null;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

}
