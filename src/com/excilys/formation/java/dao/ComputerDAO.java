package com.excilys.formation.java.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.java.mapper.MapperComputer;
import com.excilys.formation.java.model.Computer;

/**
 * 
 * @author excilys
 *
 */
public class ComputerDAO {

	private MapperComputer mapperComputer = MapperComputer.getInstance();
	private static final String LISTCOMPUTER = "SELECT * FROM computer;";
	private static final String SHOWCOMPUTERDETAILS = "SELECT * from computer WHERE name = ?;";
	private static final String SHOWCOMPUTERDETAILSBYID = "SELECT * from computer WHERE id = ?;";
	private static final String CREATECOMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES(?,?,?,?);";
	private static final String UPDATEACOMPUTER = "UPDATE computer SET name = ?,introduced = ?,discontinued = ?,company_id = ? WHERE ID = ?";
	private static final String DELETEACOMPUTER = "DELETE FROM computer WHERE id = ?;";
	private static final String SHOWCOMPUTERPAGE = "SELECT * FROM computer LIMIT ? OFFSET ?";
	
	private ComputerDAO(){}
	
	private static ComputerDAO computerDAO = new ComputerDAO();
	
	public static ComputerDAO getInstance() {
		return computerDAO;
	}
	
	/**
	 * Function use to print Computer database
	 * @return a list filled with all the Computer object found and map from database
	 */
	public List<Computer> getList() {
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		List<Computer> list = new ArrayList<Computer>();
		ResultSet results = null;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(LISTCOMPUTER)) {
			results = stmt.executeQuery();
			while(results.next()) {
				Computer computer = mapperComputer.mapper(results);
				list.add(computer);
			}
		} catch (SQLException e) {
			System.out.println("Exception due à la requète ListComputer");
			e.printStackTrace();
		}
		finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return list;
	}
	
	/**
	 * Used to show the details of computer(s) using their name as key
	 * @param name used as a key to search in Computer database
	 * @return a list filled with the Computer object found with the key
	 */
	public List<Computer> getDetailsByName(String name) {
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		List<Computer> list = new ArrayList<Computer>();
		ResultSet results = null;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(SHOWCOMPUTERDETAILS)) {
			stmt.setString(1, name);
			results = stmt.executeQuery();
			while(results.next()) {
				Computer computer = mapperComputer.mapper(results);
				list.add(computer);
			}
		} catch (SQLException e) {
			System.out.println("Exception due à la requète SHOWCOMPUTERDETAILS");
			e.printStackTrace();
		}
		finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return list;
	}
	
	/**
	 * Used to show the details of computer(s) using their ID as key
	 * @param ID used as a key to search in Computer database
	 * @return a list filled with the Computer object found with the key
	 */
	public List<Computer> getDetailsByID(int id) {
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		List<Computer> list = new ArrayList<Computer>();
		ResultSet results = null;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(SHOWCOMPUTERDETAILSBYID)) {
			stmt.setInt(1, id);
			results = stmt.executeQuery();
			while(results.next()) {
				Computer computer = mapperComputer.mapper(results);
				list.add(computer);
			}
		} catch (SQLException e) {
			System.out.println("Exception due à la requète SHOWCOMPUTERDETAILSBYID");
			e.printStackTrace();
		}
		finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return list;
	}
	
	/**
	 * Insert a new computer in the database
	 * @param computer object created with the parameters give by the user
	 */
	public void create(Computer computer) {
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(CREATECOMPUTER)) {
			stmt.setString(1, computer.getName());
			if (computer.getIntroduced() != null) {
				stmt.setString(2, computer.getIntroduced().toString());		
				if (computer.getDiscontinued() != null) {
					stmt.setString(3, computer.getDiscontinued().toString());
				}
				else {
					stmt.setString(3, null);
				}
			}else {
				stmt.setString(2, null);
				stmt.setString(3, null);
			}
			if (computer.getCompany().getId() == 0) {
				stmt.setString(4, null);
			} else if(checkCompanyID(computer.getCompany().getId())) {
				stmt.setLong(4, computer.getCompany().getId());				
				if (stmt.executeUpdate() == 1) {
					System.out.println("Computer Created");
				} else {
					System.out.println("Computer Not Created ! ");
				}
			}
			else System.out.println("The company ID you give is wrong ... can't create the new computer");
		} catch (SQLException e) {
			System.out.println("Exception due à la requète CREATECOMPUTER");
			e.printStackTrace();
		}
		finally {
			connectionDatabase.disconnect();
		}
	}
	
	/**
	 * Check if CompanyID exist to avoid sql error
	 * @param companyID the ID we will check if it exist in Company database
	 * @return true if the ID exist false if not
	 */
	public boolean checkCompanyID(long companyID) {
//		List<Company> listCompany = CompanyDAO.getInstance().listCompany();
//		for(Company company: listCompany) {
//			if(company.getId()==companyID) {
//				return true;
//			}
//		}
		return false;
	}
	
	/**
	 * Update a computer using his ID
	 * @param computer contains the new configuration of computer
	 * @param id the ID of the computer we want to change
	 */
	public void update(Computer computer) {
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		try (PreparedStatement stmt = connectionDatabase.connect().prepareStatement(UPDATEACOMPUTER)){	
			stmt.setString(1, computer.getName());
			if (computer.getIntroduced() != null) {
				stmt.setString(2, computer.getIntroduced().toString());	
				if (computer.getDiscontinued() != null) {
					stmt.setString(3, computer.getDiscontinued().toString());				
				}
				else {
					stmt.setString(3, null);
				}
			}else {
				stmt.setString(2, null);
				stmt.setString(3, null);	
			}
			if (computer.getCompany().getId() == 0) {
				stmt.setString(4, null);
			} else if(checkCompanyID(computer.getCompany().getId())) {
				stmt.setLong(4, computer.getCompany().getId());				
				stmt.setLong(5, computer.getId());
				if (stmt.executeUpdate() == 1) {
					System.out.println("Computer Updated");
				} else {
					System.out.println("Computer Not Updated ! ");
				}
			}
			else System.out.println("The company ID you give is wrong ... can't update the computer"); 
		} catch (SQLException e) {
			System.out.println("Exception due à la requète UPDATEACOMPUTER");
			e.printStackTrace();
		}
		finally {
			connectionDatabase.disconnect();
		}
	}
	
	/**
	 * Delete a computer using his ID
	 * @param id  the ID of the computer we want to delete
	 */
	public void delete(int id) {
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(DELETEACOMPUTER)) {
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
			connectionDatabase.disconnect();
		}
	}
}
