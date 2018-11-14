package com.excilys.formation.java.cli.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.java.cli.modele.Computer;
import com.excilys.formation.java.cli.mapper.MapperComputer;

public class ComputerDAO {

	private PreparedStatement stmt;
	private ResultSet results;
	private static final String LISTCOMPUTER = "SELECT * FROM computer;";
	private static final String SHOWCOMPUTERDETAILS = "SELECT * from computer WHERE name = ?;";
	private static final String SHOWCOMPUTERDETAILSBYID = "SELECT * from computer WHERE id = ?;";
	private static final String CREATECOMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES(?,?,?,?);";
	private static final String UPDATEACOMPUTER = "UPDATE computer SET name = ?,introduced = ?,discontinued = ?,company_id = ? WHERE ID = ?";
	private static final String DELETEACOMPUTER = "DELETE FROM computer WHERE id = ?;";
	
	public List<Computer> listComputer() {
		ConnectionCLI cli = new ConnectionCLI();
		List<Computer> list = new ArrayList<Computer>();
		try {
			stmt = cli.connect().prepareStatement(LISTCOMPUTER);
			results = stmt.executeQuery();
			MapperComputer mapperComputer = new MapperComputer();
			while(results.next()) {
				Computer computer = mapperComputer.mapperComputer(results);
				list.add(computer);
			}
		} catch (SQLException e) {
			System.out.println("Exception due à la requète ListComputer");
			e.printStackTrace();
		}
		finally {
			cli.disconnect();
			if (results != null) try { results.close(); } catch (SQLException ignore) {}
	        if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
		}
		return list;
	}
	
	
	public List<Computer> showComputerDetailsByName(String name) {
		ConnectionCLI cli = new ConnectionCLI();
		List<Computer> list = new ArrayList<Computer>();
		try {
			stmt = cli.connect().prepareStatement(SHOWCOMPUTERDETAILS);
			stmt.setString(1, name);
			results = stmt.executeQuery();
			MapperComputer mapperComputer = new MapperComputer();
			while(results.next()) {
				Computer computer = mapperComputer.mapperComputer(results);
				list.add(computer);
			}
		} catch (SQLException e) {
			System.out.println("Exception due à la requète SHOWCOMPUTERDETAILS");
			e.printStackTrace();
		}
		finally {
			cli.disconnect();
			if (results != null) try { results.close(); } catch (SQLException ignore) {}
	        if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
		}
		return list;
	}
	
	public List<Computer> showComputerDetailsByID(int id) {
		ConnectionCLI cli = new ConnectionCLI();
		List<Computer> list = new ArrayList<Computer>();
		try {
			stmt = cli.connect().prepareStatement(SHOWCOMPUTERDETAILSBYID);
			stmt.setInt(1, id);
			results = stmt.executeQuery();
			MapperComputer mapperComputer = new MapperComputer();
			while(results.next()) {
				Computer computer = mapperComputer.mapperComputer(results);
				list.add(computer);
			}
		} catch (SQLException e) {
			System.out.println("Exception due à la requète SHOWCOMPUTERDETAILSBYID");
			e.printStackTrace();
		}
		finally {
			cli.disconnect();
			if (results != null) try { results.close(); } catch (SQLException ignore) {}
	        if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
		}
		return list;
	}
	
	public void createComputer(Computer computer) {
		ConnectionCLI cli = new ConnectionCLI();
		try {
			stmt = cli.connect().prepareStatement(CREATECOMPUTER);
			stmt.setString(1, computer.getName());
			if (computer.getIntroduced() != null) {
				stmt.setString(2, computer.getIntroduced().toString());				
			}else {
				stmt.setString(2, null);			
			}
			if (computer.getDiscontinued() != null) {
				stmt.setString(3, computer.getDiscontinued().toString());				
			}else {
				stmt.setString(3, null);			
			}
			if (computer.getCompanyId() == 0) {
				stmt.setString(4, null);
			} else {
				stmt.setInt(4, computer.getCompanyId());				
			}
			if (stmt.executeUpdate() == 1) {
				System.out.println("Computer Created");
			} else {
				System.out.println("Computer Not Created ! ");
			}
		} catch (SQLException e) {
			System.out.println("Exception due à la requète CREATECOMPUTER");
			e.printStackTrace();
		}
		finally {
			cli.disconnect();
	        if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
		}
	}
	
	public void updateComputer(Computer computer, int id) {
		ConnectionCLI cli = new ConnectionCLI();
		try {
			stmt = cli.connect().prepareStatement(UPDATEACOMPUTER);
			stmt.setString(1, computer.getName());
			if (computer.getIntroduced() != null) {
				stmt.setString(2, computer.getIntroduced().toString());				
			}else {
				stmt.setString(2, null);			
			}
			if (computer.getDiscontinued() != null) {
				stmt.setString(3, computer.getDiscontinued().toString());				
			}else {
				stmt.setString(3, null);			
			}
			if (computer.getCompanyId() == 0) {
				stmt.setString(4, null);
			} else {
				stmt.setInt(4, computer.getCompanyId());				
			}
			stmt.setInt(5, id);
			if (stmt.executeUpdate() == 1) {
				System.out.println("Computer Updated");
			} else {
				System.out.println("Computer Not Updated ! ");
			}
		} catch (SQLException e) {
			System.out.println("Exception due à la requète UPDATEACOMPUTER");
			e.printStackTrace();
		}
		finally {
			cli.disconnect();
	        if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
		}
	}
	
	public void deleteComputer(int id) {
		ConnectionCLI cli = new ConnectionCLI();
		try {
			stmt = cli.connect().prepareStatement(DELETEACOMPUTER);
			stmt.setInt(1, id);
			if(stmt.executeUpdate() == 1) {
				System.out.println("Computer Deleted");
			}else {
				System.out.println("ID unreachable");
			}
		} catch (SQLException e) {
			System.out.println("Exception due à la requète DELETEACOMPUTER");
			e.printStackTrace();
		}
		finally {
			cli.disconnect();
	        if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
		}
	}
}
