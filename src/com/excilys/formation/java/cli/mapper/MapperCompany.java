/**
 * 
 */
package com.excilys.formation.java.cli.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.java.cli.modele.Company;

/**
 * @author excilys
 *
 */
public class MapperCompany {
	
	public Company mapperCompany(ResultSet results) throws SQLException {
		Company company = new Company();
		company.setId(results.getInt(1));
		company.setName(results.getString(2));
		
		return company;
	}

}
