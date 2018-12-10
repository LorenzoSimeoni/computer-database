package com.excilys.formation.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Page;
import com.excilys.formation.rawmapper.MapperRawComputer;

/**
 * 
 * @author excilys
 *
 */
@Repository
public class ComputerDAO {

	private final static Logger LOGGER = LogManager.getLogger(ComputerDAO.class.getName());
	@Autowired
	ConnectionDatabase connectionDatabase;
	private static final String LISTCOMPUTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id;";
	private static final String SHOWCOMPUTERDETAILS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
			+ " FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE name = :name;";
	private static final String SHOWCOMPUTERDETAILSBYID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
			+ " FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = :id;";
	private static final String SHOWCOMPUTERBYCOMPANYID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
			+ " FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company_id = :id;";
	private static final String CREATECOMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES(?,?,?,?);";
	private static final String UPDATEACOMPUTER = "UPDATE computer SET name = :name,introduced = :introduced, discontinued = :discontinued, company_id = :companyId WHERE id = :id;";
	private static final String DELETEACOMPUTER = "DELETE FROM computer WHERE id = ?;";
	private static final String SHOWCOMPUTERPAGE = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
			+ " FROM computer LEFT JOIN company ON computer.company_id = company.id LIMIT :limit, :offset;";
	private static final String COUNTCOMPUTER = "SELECT COUNT(computer.name) FROM computer;";
	private static final String COUNTSEARCHCOMPUTER = "SELECT COUNT(computer.name) FROM computer LEFT JOIN company ON computer.company_id = company.id"
			+ " WHERE computer.name LIKE :nameComputer OR company.name LIKE :nameCompany;";
	private static final String SEARCHCOMPUTERANDCOMPANY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE :nameComputer OR company.name LIKE :nameCompany ORDER BY ";
	private static final String SHOWORDERBY = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY ";
	private static final String LIMIT = " LIMIT :limit, :offset;";

	private ComputerDAO() {
	}

	private static ComputerDAO computerDAO = new ComputerDAO();

	public static ComputerDAO getInstance() {
		return computerDAO;
	}

	public List<Computer> getList() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(connectionDatabase.getDataSource());
		RowMapper<Computer> rowMapper = new MapperRawComputer();
		List<Computer> list = new ArrayList<Computer>();
		try {
			list = jdbcTemplate.query(LISTCOMPUTER, rowMapper);
		} catch (Exception e) {
			LOGGER.error("Can't execute the request getList", e);
		}
		return list;
	}

	public int countComputer() {
		int count = 0;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(connectionDatabase.getDataSource());
		try {
			count = jdbcTemplate.queryForObject(COUNTCOMPUTER, Integer.class);
		} catch (Exception e) {
			LOGGER.error("Can't execute the request countComputer", e);
		}
		return count;
	}

	public int countComputerLike(String name) {
		int count = 0;
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(connectionDatabase.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("nameComputer", '%' + name + '%');
		params.addValue("nameCompany", '%' + name + '%');
		try {
			count = jdbcTemplate.queryForObject(COUNTSEARCHCOMPUTER, params, Integer.class);
		} catch (Exception e) {
			LOGGER.error("Can't execute the request countComputer", e);
		}
		return count;
	}

	public List<Computer> getListPage(Page page) {
		List<Computer> list = new ArrayList<Computer>();
		RowMapper<Computer> rowMapper = new MapperRawComputer();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(connectionDatabase.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("limit", page.getLimit());
		params.addValue("offset", page.getOffset());
		try {
			list = jdbcTemplate.query(SHOWCOMPUTERPAGE, params, rowMapper);
		} catch (Exception e) {
			LOGGER.error("Can't execute the request getListPage", e);
		}
		return list;
	}

	public List<Computer> getListOrderBy(OrderByComputer column, OrderByMode mode, Page page) {
		List<Computer> list = new ArrayList<Computer>();
		String order = SHOWORDERBY + column + " " + mode + LIMIT;
		RowMapper<Computer> rowMapper = new MapperRawComputer();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(connectionDatabase.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("limit", page.getLimit());
		params.addValue("offset", page.getOffset());
		try {
			list = jdbcTemplate.query(order, params, rowMapper);
		} catch (Exception e) {
			LOGGER.error("Can't execute the request getListOrderBy", e);
		}
		return list;
	}

	public List<Computer> getComputerOrderByLike(OrderByComputer column, OrderByMode mode, String name, Page page) {
		List<Computer> list = new ArrayList<Computer>();
		String order = SEARCHCOMPUTERANDCOMPANY + column + " " + mode + LIMIT;
		RowMapper<Computer> rowMapper = new MapperRawComputer();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(connectionDatabase.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("limit", page.getLimit());
		params.addValue("offset", page.getOffset());
		params.addValue("nameComputer", '%' + name + '%');
		params.addValue("nameCompany", '%' + name + '%');
		try {
			list = jdbcTemplate.query(order, params, rowMapper);
		} catch (Exception e) {
			LOGGER.error("Can't execute the request getComputerOrderByLike", e);
		}
		return list;
	}

	/**
	 * Used to show the details of computer(s) using their name as key
	 * 
	 * @param name used as a key to search in Computer database
	 * @return a list filled with the Computer object found with the key
	 */
	public List<Computer> getDetailsByName(String name) {
		List<Computer> list = new ArrayList<Computer>();
		RowMapper<Computer> rowMapper = new MapperRawComputer();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(connectionDatabase.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", name);
		try {
			list = jdbcTemplate.query(SHOWCOMPUTERDETAILS, params, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Can't execute the request GetDetailsByID", e);
		}
		return list;
	}

	/**
	 * Used to show the details of computer(s) using their ID as key
	 * 
	 * @param ID used as a key to search in Computer database
	 * @return a list filled with the Computer object found with the key
	 */
	public Optional<Computer> getDetailsByID(long id) {
		Computer computer = null;
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(connectionDatabase.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		RowMapper<Computer> rowMapper = new MapperRawComputer();
		params.addValue("id", id);
		try {
			computer = jdbcTemplate.queryForObject(SHOWCOMPUTERDETAILSBYID, params, rowMapper);
		} catch (Exception e) {
			LOGGER.error("Can't execute the request GetDetailsByID", e);
		}
		return Optional.ofNullable(computer);
	}

	public List<Computer> getDetailsByCompanyID(long id) {
		List<Computer> list = new ArrayList<Computer>();
		RowMapper<Computer> rowMapper = new MapperRawComputer();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(connectionDatabase.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		try {
			list = jdbcTemplate.query(SHOWCOMPUTERBYCOMPANYID, params, rowMapper);
		} catch (Exception e) {
			LOGGER.error("Can't execute the request GetDetailsByCompanyID", e);
		}
		return list;
	}

	/**
	 * Insert a new computer in the database
	 * 
	 * @param computer object created with the parameters give by the user
	 */
	public void create(Computer computer) {
		int numberOfCreatedElement;
		try (PreparedStatement stmt = connectionDatabase.connect().prepareStatement(CREATECOMPUTER)) {
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
			if (computer.getCompany().getId() == 0) {
				stmt.setString(4, null);
			} else {
				stmt.setLong(4, computer.getCompany().getId());
			}
			numberOfCreatedElement = stmt.executeUpdate();
			LOGGER.info(numberOfCreatedElement + " elements with ID : " + computer.getId() + " are now created");
		} catch (SQLException e) {
			LOGGER.error("Can't execute the request create", e);
		} finally {
			connectionDatabase.disconnect();
		}
	}

	/**
	 * Update a computer using his ID
	 * 
	 * @param computer contains the new configuration of computer
	 * @param id       the ID of the computer we want to change
	 */
	public Computer update2(Computer computer) {
		int numberOfUpdatedElement = 0;
		try (PreparedStatement stmt = connectionDatabase.connect().prepareStatement(UPDATEACOMPUTER)) {
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
			if (computer.getCompany().getId() == 0) {
				stmt.setString(4, null);
			} else {
				stmt.setLong(4, computer.getCompany().getId());
			}
			stmt.setLong(5, computer.getId());
			numberOfUpdatedElement = stmt.executeUpdate();
			LOGGER.info(numberOfUpdatedElement + " elements with ID : " + computer.getId() + " are now updated");
		} catch (SQLException e) {
			LOGGER.error("Can't execute the request update", e);
		} finally {
			connectionDatabase.disconnect();
		}
		return computer;
	}

	public Computer update(Computer computer) {
		int numberOfUpdatedElement = 0;
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(connectionDatabase.getDataSource());
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", computer.getName());
		params.addValue("introduced", computer.getIntroduced());
		params.addValue("discontinued", computer.getDiscontinued());
		params.addValue("idCompany", computer.getCompany().getId());
		params.addValue("id", computer.getId());
		try {
			numberOfUpdatedElement = jdbcTemplate.update(UPDATEACOMPUTER, params);
			LOGGER.info(numberOfUpdatedElement + " elements with ID : " + computer.getId() + " are now updated");
		} catch (Exception e) {
			LOGGER.error("Can't execute the request update", e);
		}
		return computer;
	}

	/**
	 * Delete a computer using his ID
	 * 
	 * @param id the ID of the computer we want to delete
	 */
	public int delete(long id) {
		int numberOfDeletedElement = 0;
		try (PreparedStatement stmt = connectionDatabase.connect().prepareStatement(DELETEACOMPUTER)) {
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
