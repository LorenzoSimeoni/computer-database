package com.excilys.formation.java.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.java.mapper.MapperCompany;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.validator.Validator;

/**
 * 
 * @author excilys
 *
 */
public class CompanyDAO {
	
	private final static Logger LOGGER = LogManager.getLogger(Validator.class.getName());
	private MapperCompany mapperCompany = MapperCompany.getInstance();
	private static final String LISTCOMPANY = "SELECT * FROM company;";
	private static final String LISTCOMPANYDETAILSBYID = "SELECT * FROM company WHERE id = ?;";
	private static final String SHOWCOMPANYPAGE = "SELECT * FROM company LIMIT ?, ?";
	
	private CompanyDAO(){}
	
	private static CompanyDAO companyDAO = new CompanyDAO();
	
	public static CompanyDAO getInstance() {
		return companyDAO;
	}
	
	/**
	 * Function use to print Company database
	 * @return a list filled with all the Company object found in database
	 */
	public List<Company> getList() {
		ResultSet results = null;
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		List<Company> list = new ArrayList<Company>();
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(LISTCOMPANY)) {
			results = stmt.executeQuery();
			while(results.next()) {
				Company company = mapperCompany.mapper(results);
				list.add(company);
			}
		} catch (SQLException e) {
			LOGGER.info("Can't execute the request LISTCOMPANY", e);
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return list;
	}
	
	/**
	 * Function use to print Company database
	 * @return a list filled with all the Company object found in database
	 */
	public Company getDetailsById(long id) {
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		Company company = null;
		ResultSet results = null;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(LISTCOMPANYDETAILSBYID)) {
			stmt.setLong(1, id);
			results = stmt.executeQuery();
			while(results.next()) {
				company = mapperCompany.mapper(results);				
			}
		} catch (SQLException e) {
			LOGGER.info("Can't execute the request LISTCOMPANYDETAILSBYID", e);
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return company;
	}
	
	/**
	 * Function use to print Company database with page
	 * @param limit
	 * @param offset
	 * @return
	 */
	public List<Company> getListPage(Page page) {
		ResultSet results = null;
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		List<Company> list = new ArrayList<Company>();
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(SHOWCOMPANYPAGE)) {
			stmt.setInt(1, page.getLimit());
			stmt.setInt(2, page.getOffset());
			results = stmt.executeQuery();
			while(results.next()) {
				Company company = mapperCompany.mapper(results);
				list.add(company);
			}
		} catch (SQLException e) {
			LOGGER.info("Can't execute the request SHOWCOMPANYPAGE", e);
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return list;
	}
}
