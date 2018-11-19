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
	private static final String SHOWCOMPANYPAGE = "SELECT * FROM computer LIMIT ? OFFSET ?";
	
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
		PreparedStatement stmt;
		ResultSet results;
		ConnectionDatabase connectionDatabase = ConnectionDatabase.getInstance();
		List<Company> list = new ArrayList<Company>();
		try {
			stmt = connectionDatabase.connect().prepareStatement(LISTCOMPANY);
			results = stmt.executeQuery();
			while(results.next()) {
				Company company = mapperCompany.mapper(results);
				list.add(company);
			}
		} catch (SQLException e) {
			System.out.println("Exception due à la requète LISTCOMPANY");
			e.printStackTrace();
		}
		finally {
			connectionDatabase.disconnect();
		}
		return list;
	}
}
