package com.excilys.formation.java.cli.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDAO {
	
	private PreparedStatement stmt;
	private ResultSet results;
	private static final String LISTCOMPANY = "SELECT * FROM Company;";
	
	public ResultSet listCompany() {
		ConnectionCLI cli = new ConnectionCLI();
		try {
			stmt = cli.connect().prepareStatement(LISTCOMPANY);
			results = stmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("Exception due à la requète LISTCOMPANY");
			e.printStackTrace();
		}
		cli.disconnect();
		return results;
	}
}
