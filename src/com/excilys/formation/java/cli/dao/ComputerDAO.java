package com.excilys.formation.java.cli.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComputerDAO {

	private PreparedStatement stmt;
	private ResultSet results;
	private static final String LISTCOMPUTER = "SELECT * FROM computer;";
	private static final String SHOWCOMPUTERDETAILS = "SELECT * from computer WHERE id = ?;";
	private static final String CREATECOMPUTER = "INSERT INTO computer VALUES(?,?,?,?);";
	private static final String UPDATEACOMPUTER = "UPDATE COMPUTER SET ? = ? WHERE ID = ?";
	private static final String DELETEACOMPUTER = "DELETE FROM computer WHERE id = ?;";
	
	public ResultSet listComputer() {
		ConnectionCLI cli = new ConnectionCLI();
		try {
			stmt = cli.connect().prepareStatement(LISTCOMPUTER);
			results = stmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("Exception due à la requète ListComputer");
			e.printStackTrace();
		}
		cli.disconnect();
		return results;
	}
	
	public ResultSet showComputerDetails(int id) {
		ConnectionCLI cli = new ConnectionCLI();
		try {
			stmt = cli.connect().prepareStatement(SHOWCOMPUTERDETAILS);
			stmt.setInt(1, id);
			results = stmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("Exception due à la requète SHOWCOMPUTERDETAILS");
			e.printStackTrace();
		}
		cli.disconnect();
		return results;
	}
	
	public void createComputer() {
		ConnectionCLI cli = new ConnectionCLI();
		try {
			stmt = cli.connect().prepareStatement(CREATECOMPUTER);
			//TODO stmt.setStrings
			results = stmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("Exception due à la requète CREATECOMPUTER");
			e.printStackTrace();
		}
		cli.disconnect();
	}
	
	public void updateComputer() {
		ConnectionCLI cli = new ConnectionCLI();
		try {
			stmt = cli.connect().prepareStatement(UPDATEACOMPUTER);
			//TODO stmt.setStrings
			results = stmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("Exception due à la requète UPDATEACOMPUTER");
			e.printStackTrace();
		}
		cli.disconnect();
	}
	
	public void deleteComputer(int id) {
		ConnectionCLI cli = new ConnectionCLI();
		try {
			stmt = cli.connect().prepareStatement(DELETEACOMPUTER);
			stmt.setInt(1, id);
			results = stmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("Exception due à la requète DELETEACOMPUTER");
			e.printStackTrace();
		}
		cli.disconnect();
	}
}
