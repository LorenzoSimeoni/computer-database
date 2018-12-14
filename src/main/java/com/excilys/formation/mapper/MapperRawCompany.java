package com.excilys.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.model.Company;

public class MapperRawCompany implements RowMapper<Company> {
	
	private MapperCompany mapperCompany;
	
	@Autowired
	public MapperRawCompany(MapperCompany mapperCompany) {
		this.mapperCompany = mapperCompany;
	}
	
	@Override
	public Company mapRow(ResultSet results, int rowNum) throws SQLException {
		return mapperCompany.mapper(results);
	}
}
