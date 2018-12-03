/**
 * 
 */
package com.excilys.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.model.Company;

/**
 * @author excilys
 *
 */
public class MapperCompany {
	
	private MapperCompany(){}
	
	private static MapperCompany mapperCompany = new MapperCompany();
	
	public static MapperCompany getInstance() {
		return mapperCompany;
	}
	
	/**
	 * Map a ResultSet in a Company object
	 * @param results contains what we found in database
	 * @return Constructed company object
	 * @throws SQLException
	 */
	public Company mapper(ResultSet results) throws SQLException {
		Company company = new Company.CompanyBuilder(results.getLong(1)).setName(results.getString(2)).build();
		
		return company;
	}

}
