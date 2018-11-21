/**
 * 
 */
package com.excilys.formation.java.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.validator.validator;

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
	
	/**
	 * Construct a computer object from a ResultSet (Select)
	 * @param results contains what we found in database 
	 * @return Computer object
	 * @throws SQLException
	 */
	public Computer mapper(ResultSet results) throws SQLException {
		Company company = new Company.CompanyBuilder(results.getLong(5)).build();
		
		String introduced = results.getString(3);
		LocalDateTime introducedDateAndTime=null;
		if(introduced != null) {
			introduced = introduced.replace(' ', 'T');
			introducedDateAndTime = LocalDateTime.parse(introduced);
		}
		LocalDateTime discontinuedDateAndTime=null;
		String discontinued = results.getString(3);
		if( discontinued != null) {
			discontinued = discontinued.replace(' ', 'T');
			discontinuedDateAndTime = LocalDateTime.parse(discontinued);
		}
		
		Computer computer = new Computer.ComputerBuilder(results.getString(2))
				.setID(results.getInt(1))
				.setIntroduced(introducedDateAndTime)
				.setDiscontinued(discontinuedDateAndTime)
				.setCompanyId(company)
				.build();
			
		return computer;
	}
	
	/**
	 * Construct a Computer object from arguments give by the user (update or user)
	 * @param name 
	 * @param introduced
	 * @param discontinued
	 * @param companyId
	 * @return Computer object
	 */
	public Computer mapper(long id, String name, String introduced, String discontinued, String companyId) {
		Company company;
		Computer computer;
		
		LocalDateTime localIntroduced = null;
		LocalDateTime localDiscontinued = null;
		if(introduced != null && validator.testStringIsADate(introduced)) {
			localIntroduced = LocalDateTime.parse(introduced);
			if(discontinued != null && validator.testStringIsADate(discontinued)) {
				localDiscontinued = LocalDateTime.parse(discontinued);
				if(localDiscontinued.isBefore(localIntroduced)) {
					localDiscontinued = null;
				}
			}
		}
		if(companyId != null) {
			company = new Company.CompanyBuilder(Long.parseLong(companyId)).build();		
		}
		else {
			company = new Company.CompanyBuilder().build();
		}
		computer = new Computer.ComputerBuilder(name)
				.setID(id)
				.setIntroduced(localIntroduced)
				.setDiscontinued(localDiscontinued)
				.setCompanyId(company)
				.build();			
		
		return computer;
	}
	
	/**
	 * Construct a Computer object from arguments give by the user (update or user)
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param companyId
	 * @return
	 */
	public Computer mapper(String name, String introduced, String discontinued, String companyId) {
		Company company;
		Computer computer;
		
		LocalDateTime localIntroduced = null;
		LocalDateTime localDiscontinued = null;
		if(introduced != null && validator.testStringIsADate(introduced)) {
			localIntroduced = LocalDateTime.parse(introduced);
			if(discontinued != null && validator.testStringIsADate(discontinued)) {
				localDiscontinued = LocalDateTime.parse(discontinued);
				if(localDiscontinued.isBefore(localIntroduced)) {
					localDiscontinued = null;
				}
			}
		}
		if(companyId != null) {
			company = new Company.CompanyBuilder(Long.parseLong(companyId)).build();		
		} else {
			company = new Company.CompanyBuilder().build();
		}
		
		computer = new Computer.ComputerBuilder(name)
				.setIntroduced(localIntroduced)
				.setDiscontinued(localDiscontinued)
				.setCompanyId(company)
				.build();			
		return computer;
	}
}
