/**
 * 
 */
package com.excilys.formation.java.cli.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import com.excilys.formation.java.cli.modele.Computer;

/**
 * @author excilys
 *
 */
public class MapperComputer {
	
	private MapperComputer(){}
	
	private static MapperComputer mapperComputer = new MapperComputer();
	
	public static MapperComputer getInstance() {
		return mapperComputer;
	}
	
	public Computer mapperComputer(ResultSet results) throws SQLException {		
		Computer computer = new Computer();
		
		computer.setId(results.getInt(1));
		computer.setName(results.getString(2));
		String introduced = results.getString(3);
		LocalDateTime dateAndTime=null;
		if(introduced != null) {
			introduced = introduced.replace(' ', 'T');
			dateAndTime = LocalDateTime.parse(introduced);
		}
		computer.setIntroduced(dateAndTime);
		String discontinued = results.getString(3);
		if( discontinued != null) {
			discontinued = discontinued.replace(' ', 'T');
			dateAndTime = LocalDateTime.parse(discontinued);
		}
		computer.setDiscontinued(dateAndTime);
		computer.setCompanyId(results.getInt(5));
			
		return computer;
	}

	public Computer mapperComputer(String name, String introduced, String discontinued, String companyId) {
		Computer computer = new Computer();
		
		LocalDateTime localIntroduced = null;
		LocalDateTime localDiscontinued = null;
		if(!introduced.toLowerCase().equals("null")) {
			localIntroduced = LocalDateTime.parse(introduced);
			if(!discontinued.toLowerCase().equals("null")) {
				localDiscontinued = LocalDateTime.parse(discontinued);
				if(localDiscontinued.isBefore(localIntroduced)) {
					localDiscontinued = null;
				}
			}
		}
		computer.setName(name);
		computer.setIntroduced(localIntroduced);
		computer.setDiscontinued(localDiscontinued);
		if(companyId != null) {
			computer.setCompanyId(Integer.parseInt(companyId));			
		}
		else {
			computer.setCompanyId(0);
		}
		
		return computer;
	}
}
