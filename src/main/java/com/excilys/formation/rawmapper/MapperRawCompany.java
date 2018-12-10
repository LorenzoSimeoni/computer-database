package com.excilys.formation.rawmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.model.Company;

public class MapperRawCompany implements RowMapper<Company> {
	
	@Override
	public Company mapRow(ResultSet results, int rowNum) throws SQLException {
		Company company = new Company.CompanyBuilder(results.getLong(1)).setName(results.getString(2)).build();	
		return company;
	}
}
