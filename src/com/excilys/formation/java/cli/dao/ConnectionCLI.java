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
	
	// create properties
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
