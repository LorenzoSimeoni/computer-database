package com.excilys.formation.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.model.Computer;

public class MapperRawComputer implements RowMapper<Computer>{
	
	private MapperComputer mapperComputer;
	
	@Autowired
	public MapperRawComputer(MapperComputer mapperComputer) {
		this.mapperComputer = mapperComputer;
	}
	
	@Override
	public Computer mapRow(ResultSet results, int rowNum) throws SQLException {
		return mapperComputer.mapper(results);
	}
}
