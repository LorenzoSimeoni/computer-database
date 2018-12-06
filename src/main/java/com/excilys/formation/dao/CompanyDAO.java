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

import com.excilys.formation.mapper.MapperCompany;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Page;

/**
 * 
 * @author excilys
 *
 */
public class CompanyDAO {
	
	private final static Logger LOGGER = LogManager.getLogger(CompanyDAO.class.getName());
	@Autowired
	private MapperCompany mapperCompany;
	private static final String LISTCOMPANY = "SELECT id, name FROM company;";
	private static final String LISTCOMPANYDETAILSBYID = "SELECT id, name FROM company WHERE id = ?;";
	private static final String SHOWCOMPANYPAGE = "SELECT id, name FROM company LIMIT ?, ?";
	private static final String DELETEACOMPANY = "DELETE FROM company WHERE id = ?;";

	private ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
	
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
		List<Company> list = new ArrayList<Company>();
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(LISTCOMPANY)) {
			results = stmt.executeQuery();
			while(results.next()) {
				Company company = mapperCompany.mapper(results);
				list.add(company);
			}
		} catch (SQLException e) {
			LOGGER.error("Can't execute the request LISTCOMPANY", e);
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
	public Optional<Company> getDetailsById(long id) {
		Company company = null;
		ResultSet results = null;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(LISTCOMPANYDETAILSBYID)) {
			stmt.setLong(1, id);
			results = stmt.executeQuery();
			while(results.next()) {
				company = mapperCompany.mapper(results);				
			}
		} catch (SQLException e) {
			LOGGER.error("Can't execute the request LISTCOMPANYDETAILSBYID", e);
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return Optional.ofNullable(company);
	}
	
	/**
	 * Function use to print Company database with page
	 * @param limit
	 * @param offset
	 * @return
	 */
	public List<Company> getListPage(Page page) {
		ResultSet results = null;
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
			LOGGER.error("Can't execute the request SHOWCOMPANYPAGE", e);
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return list;
	}
	
	public int delete(long id) {
		
		int numberOfDeletedElement = 0;
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(DELETEACOMPANY)) {
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
