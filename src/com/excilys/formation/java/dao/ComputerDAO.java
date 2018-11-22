package com.excilys.formation.java.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.java.mapper.MapperComputer;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.validator.Validator;

/**
 * 
 * @author excilys
 *
 */
public class ComputerDAO {

	private final static Logger LOGGER = LogManager.getLogger(Validator.class.getName());
	private MapperComputer mapperComputer = MapperComputer.getInstance();
	private static final String LISTCOMPUTER = "SELECT * FROM computer;";
	private static final String SHOWCOMPUTERDETAILS = "SELECT * from computer WHERE name = ?;";
	private static final String SHOWCOMPUTERDETAILSBYID = "SELECT * from computer WHERE id = ?;";
	private static final String CREATECOMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES(?,?,?,?);";
	private static final String UPDATEACOMPUTER = "UPDATE computer SET name = ?,introduced = ?,discontinued = ?,company_id = ? WHERE ID = ?";
	private static final String DELETEACOMPUTER = "DELETE FROM computer WHERE id = ?;";
	private static final String SHOWCOMPUTERPAGE = "SELECT * FROM computer LIMIT ?, ?";
	
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
			LOGGER.info("Can't execute the request getList", e);
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return list;
	}
	
	public List<Computer> getListPage(int limit, int offset) {
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		List<Computer> list = new ArrayList<Computer>();
		ResultSet results = null;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(SHOWCOMPUTERPAGE)) {
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			results = stmt.executeQuery();
			while(results.next()) {
				Computer computer = mapperComputer.mapper(results);
				list.add(computer);
			}
		} catch (SQLException e) {
			LOGGER.info("Can't execute the request getListPage", e);
		} finally {
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
			LOGGER.info("Can't execute the request GetDetailsByName", e);
		} finally {
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
	public Computer getDetailsByID(long id) {
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		Computer computer = new Computer.ComputerBuilder().build();
		ResultSet results = null;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(SHOWCOMPUTERDETAILSBYID)) {
			stmt.setLong(1, id);
			results = stmt.executeQuery();
			while(results.next()) {
				computer = mapperComputer.mapper(results);
			}
		} catch (SQLException e) {
			LOGGER.info("Can't execute the request GetDetailsByID", e);
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return computer;
	}
	
	/**
	 * Insert a new computer in the database
	 * @param computer object created with the parameters give by the user
	 */
	public void create(Computer computer) {
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		int numberOfCreatedElement;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(CREATECOMPUTER)) {
			stmt.setString(1, computer.getName());
			if (computer.getIntroduced() != null) {
				stmt.setString(2, computer.getIntroduced().toString());		
				if (computer.getDiscontinued() != null) {
					stmt.setString(3, computer.getDiscontinued().toString());
				} else {
					stmt.setString(3, null);
				}
			} else {
				stmt.setString(2, null);
				stmt.setString(3, null);	
			}
			if(computer.getCompany().getId() == 0) {
				stmt.setString(4, null);
			} else {
				stmt.setLong(4, computer.getCompany().getId());	
			}
			numberOfCreatedElement = stmt.executeUpdate();
			LOGGER.info(numberOfCreatedElement+" elements with ID : "+computer.getId()+ " are now created");
		} catch (SQLException e) {
			LOGGER.info("Can't execute the request create", e);
		} finally {
			connectionDatabase.disconnect();
		}
	}
	
	/**
	 * Update a computer using his ID
	 * @param computer contains the new configuration of computer
	 * @param id the ID of the computer we want to change
	 */
	public Computer update(Computer computer) {
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		int numberOfUpdatedElement = 0;
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
			} else {
				stmt.setLong(4, computer.getCompany().getId());				
			}
			stmt.setLong(5, computer.getId());
			numberOfUpdatedElement = stmt.executeUpdate();
			LOGGER.info(numberOfUpdatedElement+" elements with ID : "+computer.getId()+ " are now updated");
		} catch (SQLException e) {
			LOGGER.info("Can't execute the request update", e);
		} finally {
			connectionDatabase.disconnect();
		}
		return computer;
	}
	
	/**
	 * Delete a computer using his ID
	 * @param id  the ID of the computer we want to delete
	 */
	public int delete(long id) {
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		int numberOfDeletedElement = 0;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(DELETEACOMPUTER)) {
			stmt.setLong(1, id);
			numberOfDeletedElement = stmt.executeUpdate();
			LOGGER.info(numberOfDeletedElement + " elements with ID : " + id + " are now deleted");
		} catch (SQLException e) {
			LOGGER.info("Can't execute the request delete", e);
		} finally {
			connectionDatabase.disconnect();
		}
		return numberOfDeletedElement;
	}
}
