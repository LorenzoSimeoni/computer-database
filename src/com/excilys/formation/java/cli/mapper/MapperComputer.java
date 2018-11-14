/**
 * 
 */
package com.excilys.formation.java.cli.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.java.cli.modele.Computer;

/**
 * @author excilys
 *
 */
public class MapperComputer {
	
	private Computer computer;
	
	public Computer mapperComputer(ResultSet results) throws SQLException {
		
		computer.setId(results.getInt(1));
		computer.setName(results.getString(2));
		computer.setIntroduced(results.getDate(3));
		computer.setDiscontinued(results.getDate(4));
		computer.setCompany_id(results.getInt(5));
			
		return computer;
	}
}
