package com.excilys.formation.java.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.java.mapper.MapperCompany;
import com.excilys.formation.java.model.Company;

/**
 * 
 * @author excilys
 *
 */
public class CompanyDAO {
	
	private MapperCompany mapperCompany = MapperCompany.getInstance();
	private static final String LISTCOMPANY = "SELECT * FROM company;";
	private static final String SHOWCOMPANYPAGE = "SELECT * FROM computer LIMIT ?, ?";
	
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
			System.out.println("Exception due à la requète LISTCOMPANY");
			e.printStackTrace();
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return list;
	}
	
	/**
	 * Function use to print Company database with page
	 * @param limit
	 * @param offset
	 * @return
	 */
	public List<Company> getListPage(int limit, int offset) {
		ResultSet results = null;
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		List<Company> list = new ArrayList<Company>();
		try(PreparedStatement stmt = connectionDatabase.connect().prepareStatement(SHOWCOMPANYPAGE)) {
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			results = stmt.executeQuery();
			while(results.next()) {
				Company company = mapperCompany.mapper(results);
				list.add(company);
			}
		} catch (SQLException e) {
			System.out.println("Exception due à la requète SHOWCOMPANYPAGE");
			e.printStackTrace();
		} finally {
			if(results != null) try { results.close(); } catch (SQLException ignore) {}
			connectionDatabase.disconnect();
		}
		return list;
	}
}
