/**
 * 
 */
package com.excilys.formation.java.cli;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.excilys.formation.java.cli.dao.ConnectionCLI;
import com.excilys.formation.java.cli.service.FeaturesCLI;

/**
 * @author excilys
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		
		
		ConnectionCLI cli = new ConnectionCLI();
		String sql = "SELECT * FROM company;";
		ResultSet results; 
		
		try {
			Statement stmt = cli.connect().createStatement();
			results  = stmt.executeQuery(sql);
			
			System.out.println("----------------------------------------");
			System.out.println("Name");
			System.out.println("----------------------------------------");
			System.out.println(results);
			while(results.next()) {
				System.out.println(results);
				System.out.println(results.getString(2));
			}
			System.out.println("----------------------------------------");

		} catch (SQLException e) {
			System.out.println("Exception due à la requète");
			e.printStackTrace();
		}
		
		
		cli.disconnect();
		
		
		FeaturesCLI showComputer = new FeaturesCLI();
		
		String[] arguments = {"listcomputer"};
		showComputer.features(arguments);
	}

}
