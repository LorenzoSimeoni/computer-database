package com.excilys.formation.dao;

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

import com.excilys.formation.mapper.MapperRawCompany;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Page;

/**
 * 
 * @author excilys
 *
 */
@Repository
public class CompanyDAO {

	private final static Logger LOGGER = LogManager.getLogger(CompanyDAO.class.getName());
	private static final String LISTCOMPANY = "SELECT id, name FROM company;";
	private static final String LISTCOMPANYDETAILSBYID = "SELECT id, name FROM company WHERE id = :id;";
	private static final String SHOWCOMPANYPAGE = "SELECT id, name FROM company LIMIT :limit, :offset";
	private static final String DELETEACOMPANY = "DELETE FROM company WHERE id = :id;";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private RowMapper<Company> rowMapper = new MapperRawCompany();
	@Autowired
	MapSqlParameterSource params;



	private CompanyDAO() {
	}

	private static CompanyDAO companyDAO = new CompanyDAO();

	public static CompanyDAO getInstance() {
		return companyDAO;
	}

	public List<Company> getList() {
		List<Company> list = new ArrayList<Company>();
		try {
			list = jdbcTemplate.query(LISTCOMPANY, rowMapper);
		} catch (Exception e) {
			LOGGER.error("Can't execute the request LISTCOMPANY", e);
		}
		return list;
	}

	public Optional<Company> getDetailsById(long id) {
		Company company = null;
		params.addValue("id", id);
		try {
			company = namedParameterJdbcTemplate.queryForObject(LISTCOMPANYDETAILSBYID, params, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			LOGGER.error("Can't execute the request LISTCOMPANYDETAILSBYID", e);
		}
		return Optional.ofNullable(company);
	}

	public List<Company> getListPage(Page page) {
		List<Company> list = new ArrayList<Company>();
		params.addValue("limit", page.getLimit());
		params.addValue("offset", page.getOffset());
		try {
			list = namedParameterJdbcTemplate.query(SHOWCOMPANYPAGE, params, rowMapper);
		} catch (Exception e) {
			LOGGER.error("Can't execute the request SHOWCOMPANYPAGE", e);
		}
		return list;
	}
	
	public int delete(long id) {
		int numberOfDeletedElement = 0;
		params.addValue("id",id);
		try {
			numberOfDeletedElement = namedParameterJdbcTemplate.update(DELETEACOMPANY,params);
			LOGGER.info(numberOfDeletedElement + " elements with ID : " + id + " are now deleted");
		} catch (Exception e) {
			LOGGER.error("Can't execute the request delete", e);
		}
		return numberOfDeletedElement;
	}
}
