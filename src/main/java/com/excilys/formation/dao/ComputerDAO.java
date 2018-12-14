package com.excilys.formation.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private RowMapper<Computer> rowMapper;
	private MapSqlParameterSource params;
	
    @Autowired
    public ComputerDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate,
    		RowMapper<Computer> rowMapper, MapSqlParameterSource params) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = rowMapper;
        this.params = params;
    }


	private static final String LISTCOMPUTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id;";
	private static final String SHOWCOMPUTERDETAILS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
			+ " FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE name = :name;";
	private static final String SHOWCOMPUTERDETAILSBYID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
			+ " FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = :id;";
	private static final String SHOWCOMPUTERBYCOMPANYID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name"
			+ " FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE company_id = :id;";
	private static final String CREATECOMPUTER = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES(:name,:introduced,:discontinued,:companyId);";
	private static final String UPDATEACOMPUTER = "UPDATE computer SET name = :name,introduced = :introduced, discontinued = :discontinued, company_id = :companyId WHERE id = :id;";
	private static final String DELETEACOMPUTER = "DELETE FROM computer WHERE id = :id;";
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

	public List<Computer> getList() {
		List<Computer> list = new ArrayList<Computer>();
		try {
			list = jdbcTemplate.query(LISTCOMPUTER, rowMapper);
		} catch (DataAccessException e) {
			LOGGER.error("Can't execute the request getList", e);
		}
		return list;
	}

	public int countComputer() {
		int count = 0;
		try {
			count = jdbcTemplate.queryForObject(COUNTCOMPUTER, Integer.class);
		} catch (DataAccessException e) {
			LOGGER.error("Can't execute the request countComputer", e);
		}
		return count;
	}

	public int countComputerLike(String name) {
		int count = 0;
		params.addValue("nameComputer", '%' + name + '%');
		params.addValue("nameCompany", '%' + name + '%');
		try {
			count = namedParameterJdbcTemplate.queryForObject(COUNTSEARCHCOMPUTER, params, Integer.class);
		} catch (DataAccessException e) {
			LOGGER.error("Can't execute the request countComputer", e);
		}
		return count;
	}

	public List<Computer> getListPage(Page page) {
		List<Computer> list = new ArrayList<Computer>();
		params.addValue("limit", page.getLimit());
		params.addValue("offset", page.getOffset());
		try {
			list = namedParameterJdbcTemplate.query(SHOWCOMPUTERPAGE, params, rowMapper);
		} catch (DataAccessException e) {
			LOGGER.error("Can't execute the request getListPage", e);
		}
		return list;
	}

	public List<Computer> getListOrderBy(OrderByComputer column, OrderByMode mode, Page page) {
		List<Computer> list = new ArrayList<Computer>();
		String order = SHOWORDERBY + column + " " + mode + LIMIT;
		params.addValue("limit", page.getLimit());
		params.addValue("offset", page.getOffset());
		try {
			list = namedParameterJdbcTemplate.query(order, params, rowMapper);
		} catch (DataAccessException e) {
			LOGGER.error("Can't execute the request getListOrderBy", e);
		}
		return list;
	}

	public List<Computer> getComputerOrderByLike(OrderByComputer column, OrderByMode mode, String name, Page page) {
		List<Computer> list = new ArrayList<Computer>();
		String order = SEARCHCOMPUTERANDCOMPANY + column + " " + mode + LIMIT;
		params.addValue("limit", page.getLimit());
		params.addValue("offset", page.getOffset());
		params.addValue("nameComputer", '%' + name + '%');
		params.addValue("nameCompany", '%' + name + '%');
		try {
			list = namedParameterJdbcTemplate.query(order, params, rowMapper);
		} catch (DataAccessException e) {
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
		params.addValue("name", name);
		try {
			list = namedParameterJdbcTemplate.query(SHOWCOMPUTERDETAILS, params, rowMapper);
		} catch (DataAccessException e) {
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
		params.addValue("id", id);
		try {
			computer = namedParameterJdbcTemplate.queryForObject(SHOWCOMPUTERDETAILSBYID, params, rowMapper);
		} catch (DataAccessException e) {
			LOGGER.error("Can't execute the request GetDetailsByID", e);
		}
		return Optional.ofNullable(computer);
	}

	public List<Computer> getDetailsByCompanyID(long id) {
		List<Computer> list = new ArrayList<Computer>();
		params.addValue("id", id);
		try {
			list = namedParameterJdbcTemplate.query(SHOWCOMPUTERBYCOMPANYID, params, rowMapper);
		} catch (DataAccessException e) {
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
		params.addValue("name", computer.getName());
		params.addValue("introduced", computer.getIntroduced());
		params.addValue("discontinued", computer.getDiscontinued());
		if (computer.getCompany().getId() == 0) {
			params.addValue("companyId", null);	
		} else {
			params.addValue("companyId", computer.getCompany().getId());			
		}
		try {
			numberOfCreatedElement = namedParameterJdbcTemplate.update(CREATECOMPUTER, params);
			LOGGER.info(numberOfCreatedElement + " elements with ID : " + computer.getId() + " are now created");
		} catch (DataAccessException e) {
			LOGGER.error("Can't execute the request create", e);
		}
	}

	/**
	 * Update a computer using his ID
	 * 
	 * @param computer contains the new configuration of computer
	 * @param id       the ID of the computer we want to change
	 */
	public Computer update(Computer computer) {
		int numberOfUpdatedElement = 0;
		params.addValue("name", computer.getName());
		params.addValue("introduced", computer.getIntroduced());
		params.addValue("discontinued", computer.getDiscontinued());
		if (computer.getCompany().getId() == 0) {
			params.addValue("companyId", null);	
		} else {
			params.addValue("companyId", computer.getCompany().getId());			
		}
		params.addValue("id", computer.getId());
		try {
			numberOfUpdatedElement = namedParameterJdbcTemplate.update(UPDATEACOMPUTER, params);
			LOGGER.info(numberOfUpdatedElement + " elements with ID : " + computer.getId() + " are now updated");
		} catch (DataAccessException e) {
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
		params.addValue("id",id);
		try {
			numberOfDeletedElement = namedParameterJdbcTemplate.update(DELETEACOMPUTER,params);
			LOGGER.info(numberOfDeletedElement + " elements with ID : " + id + " are now deleted");
		} catch (DataAccessException e) {
			LOGGER.error("Can't execute the request delete", e);
		}
		return numberOfDeletedElement;
	}
}
