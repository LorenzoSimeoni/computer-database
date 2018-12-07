package com.excilys.formation.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.mapper.MapperComputer;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Page;

/**
 * 
 * @author excilys
 *
 */
@Repository
public class ComputerDAO {

	private final static Logger LOGGER = LogManager.getLogger(ComputerDAO.class.getName());
	@Autowired
	private MapperComputer mapperComputer;
	@Autowired
	ConnectionDatabase connectionDatabase;
	private static final String LISTCOMPUTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
			+ "FROM computer INNER JOIN company ON computer.company_id = company.id;";
	private static final String SHOWCOMPUTERDETAILS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
			+ " FROM computer INNER JOIN company ON computer.company_id = company.id WHERE name = ?;";
	private static final String SHOWCOMPUTERDETAILSBYID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
			+ " FROM computer INNER JOIN company ON computer.company_id = company.id WHERE id = ?;";
	private static final String CREATECOMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES(?,?,?,?);";
	private static final String UPDATEACOMPUTER = "UPDATE computer SET name = ?,introduced = ?,discontinued = ?,company_id = ? WHERE id = ?;";
	private static final String DELETEACOMPUTER = "DELETE FROM computer WHERE id = ?;";
	private static final String SHOWCOMPUTERPAGE = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
			+ " FROM computer INNER JOIN company ON computer.company_id = company.id LIMIT ?, ?;";
	private static final String SEARCHCOMPUTERANDCOMPANY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
			+ "FROM computer INNER JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY ";
	private static final String COUNTCOMPUTER = "SELECT COUNT(name) FROM computer;";
	private static final String COUNTSEARCHCOMPUTER = "SELECT COUNT(name) FROM computer WHERE name LIKE ? OR company_id IN (SELECT id FROM company WHERE name LIKE ?);";
	private static final String SHOWCOMPUTERBYCOMPANYID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
			+ " FROM computer INNER JOIN company ON computer.company_id = company.id WHERE company_id = ?;";
	private static final String SHOWORDERBY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
			+ "FROM computer INNER JOIN company ON computer.company_id = company.id ORDER BY ";
	private static final String LIMIT = " LIMIT ?, ?;";
	
	
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
		List<Computer> list = new ArrayList<Computer>();
		ResultSet results = null;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(LISTCOMPUTER)) {
			results = stmt.executeQuery();
			while(results.next()) {
				Computer computer = mapperComputer.mapper(results);
				list.add(computer);
			}
		} catch (SQLException e) {
			LOGGER.error("Can't execute the request getList", e);
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return list;
	}
	
	public int countComputer() {
		int count = 0;
		ResultSet results = null;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(COUNTCOMPUTER)) {
			results = stmt.executeQuery();
			while(results.next()) {
				count = results.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.error("Can't execute the request countComputer", e);
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return count;
	}
	
	public int countComputerLike(String name) {
		int count = 0;
		ResultSet results = null;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(COUNTSEARCHCOMPUTER)) {
			stmt.setString(1, '%'+name+'%');
			stmt.setString(2, '%'+name+'%');
			results = stmt.executeQuery();
			while(results.next()) {
				count = results.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.error("Can't execute the request countComputerLike", e);
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return count;
	}
	
	public List<Computer> getListPage(Page page) {
		List<Computer> list = new ArrayList<Computer>();
		ResultSet results = null;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(SHOWCOMPUTERPAGE)) {
			stmt.setInt(1, page.getLimit());
			stmt.setInt(2, page.getOffset());
			results = stmt.executeQuery();
			while(results.next()) {
				Computer computer = mapperComputer.mapper(results);
				list.add(computer);
			}
		} catch (SQLException e) {
			LOGGER.error("Can't execute the request getListPage", e);
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return list;
	}
	
	public List<Computer> getListOrderBy(OrderByComputer column, OrderByMode mode, Page page) {
		List<Computer> list = new ArrayList<Computer>();
		ResultSet results = null;
		String order = SHOWORDERBY + column + " " +mode + LIMIT;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(order)) {
			stmt.setInt(1, page.getLimit());
			stmt.setInt(2, page.getOffset());
			results = stmt.executeQuery();
			while(results.next()) {
				Computer computer = mapperComputer.mapper(results);
				list.add(computer);
			}
		} catch (SQLException e) {
			LOGGER.error("Can't execute the request getListOrderBy", e);
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
			LOGGER.error("Can't execute the request GetDetailsByName", e);
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return list;
	}
	
	public List<Computer> getComputerLike(OrderByComputer column, OrderByMode mode, String name, Page page) {
		List<Computer> list = new ArrayList<Computer>();
		ResultSet results = null;
		String order = SEARCHCOMPUTERANDCOMPANY + column + " " +mode + LIMIT;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(order)) {
			stmt.setString(1, '%'+name+'%');
			stmt.setString(2, '%'+name+'%');
			stmt.setInt(3, page.getLimit());
			stmt.setInt(4, page.getOffset());
			results = stmt.executeQuery();
			while(results.next()) {
				Computer computer = mapperComputer.mapper(results);
				list.add(computer);
			}
		} catch (SQLException e) {
			LOGGER.error("Can't execute the request getComputerLike", e);
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
	public Optional<Computer> getDetailsByID(long id) {
		Computer computer = null;
		ResultSet results = null;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(SHOWCOMPUTERDETAILSBYID)) {
			stmt.setLong(1, id);
			results = stmt.executeQuery();
			while(results.next()) {
				computer = mapperComputer.mapper(results);
			}
		} catch (SQLException e) {
			LOGGER.error("Can't execute the request GetDetailsByID", e);
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return Optional.ofNullable(computer);
	}
	
	public List<Computer> getDetailsByCompanyID(long id) {
		List<Computer> list = new ArrayList<Computer>();
		ResultSet results = null;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(SHOWCOMPUTERBYCOMPANYID)) {
			stmt.setLong(1, id);
			results = stmt.executeQuery();
			while(results.next()) {
				Computer computer = mapperComputer.mapper(results);
				list.add(computer);
			}
		} catch (SQLException e) {
			LOGGER.error("Can't execute the request GetDetailsByID", e);
		} finally {
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
			LOGGER.error("Can't execute the request create", e);
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
			LOGGER.error("Can't execute the request update", e);
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
		int numberOfDeletedElement = 0;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(DELETEACOMPUTER)) {
			stmt.setLong(1, id);
			numberOfDeletedElement = stmt.executeUpdate();
			LOGGER.info(numberOfDeletedElement + " elements with ID : " + id + " are now deleted");
		} catch (SQLException e) {
			LOGGER.error("Can't execute the request delete", e);
		} finally {
			connectionDatabase.disconnect();
		}
		return numberOfDeletedElement;
	}
}
