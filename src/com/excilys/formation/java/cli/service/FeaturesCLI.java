/**
 * 
 */
package com.excilys.formation.java.cli.service;


import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.excilys.formation.java.cli.dao.CompanyDAO;
import com.excilys.formation.java.cli.dao.ComputerDAO;
import com.excilys.formation.java.cli.mapper.MapperComputer;
import com.excilys.formation.java.cli.modele.Company;
import com.excilys.formation.java.cli.modele.Computer;

public class FeaturesCLI {
	
	public void features(String[] args) throws SQLException, ParseException {
		ComputerDAO computerDao = new ComputerDAO();
		CompanyDAO companyDao = new CompanyDAO();
		if(args[0].toLowerCase().equals("listcomputer")) {
			List<Computer> listResults = computerDao.listComputer();
			for(Computer result : listResults){
				System.out.println(result.getName());
			}
		}
		if(args[0].toLowerCase().equals("listcompany")) {
			List<Company> listResults = companyDao.listCompany();
			for(Company result : listResults) {
				System.out.println(result.getName());
			}
		}
		if(args[0].toLowerCase().equals("showcomputerdetails")) {
			List<Computer> listResults = computerDao.showComputerDetailsByName(args[1]);
			for(Computer computer : listResults){
				printComputerDetails(computer);
			}
		}
		else if(args[0].toLowerCase().equals("showcomputerdetailsbyid")) {
			List<Computer> listResults = computerDao.showComputerDetailsByID(Integer.parseInt(args[1]));
			for(Computer computer : listResults){
				printComputerDetails(computer);
			}
		}
		if(args[0].toLowerCase().equals("deleteacomputer")) {
			computerDao.deleteComputer(Integer.parseInt(args[1]));
		}
		if(args[0].toLowerCase().equals("createacomputer")) {
			MapperComputer mapperComputer = new MapperComputer();
			Computer computer = mapperComputer.mapperComputer(args[1], args[2], args[3], args[4]);
			computerDao.createComputer(computer);
		}
		if(args[0].toLowerCase().equals("updateacomputer")) {
			MapperComputer mapperComputer = new MapperComputer();
			Computer computer = mapperComputer.mapperComputer(args[2], args[3], args[4],args[5]);
			computerDao.updateComputer(computer, Integer.parseInt(args[1]));
		}
	}
	
	public void printComputerDetails(Computer computer) {
		System.out.print(computer.getId() + ", ");
		System.out.print(computer.getName() + ", ");
		System.out.print(computer.getIntroduced() + ", ");
		System.out.print(computer.getDiscontinued() + ", ");
		System.out.println(computer.getCompanyId());
	}
}
