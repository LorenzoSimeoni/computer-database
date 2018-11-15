/**
 * 
 */
package com.excilys.formation.java.cli.service;


import java.util.List;

import com.excilys.formation.java.cli.dao.CompanyDAO;
import com.excilys.formation.java.cli.dao.ComputerDAO;
import com.excilys.formation.java.cli.mapper.MapperComputer;
import com.excilys.formation.java.cli.modele.Company;
import com.excilys.formation.java.cli.modele.Computer;

public class FeaturesCLI {
	
	public void features(String[] args) {
		ComputerDAO computerDao = new ComputerDAO();
		CompanyDAO companyDao = new CompanyDAO();
		if(args[0].toLowerCase().equals("listcomputer")) {
			List<Computer> listResults = computerDao.listComputer();
			for(Computer result : listResults){
				System.out.println(result.getName());
			}
		}
		else if(args[0].toLowerCase().equals("listcompany")) {
			List<Company> listResults = companyDao.listCompany();
			for(Company result : listResults) {
				System.out.println(result.getName());
			}
		}
		else if(args[0].toLowerCase().equals("showcomputerdetails")) {
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
		else if(args[0].toLowerCase().equals("deleteacomputer")) {
			computerDao.deleteComputer(Integer.parseInt(args[1]));
		}
		else if(args[0].toLowerCase().equals("createacomputer")) {
			MapperComputer mapperComputer = new MapperComputer();
			Computer computer = mapperComputer.mapperComputer(args[1], args[2], args[3], args[4]);
			computerDao.createComputer(computer);
		}
		else if(args[0].toLowerCase().equals("updateacomputer")) {
			MapperComputer mapperComputer = new MapperComputer();
			Computer computer = mapperComputer.mapperComputer(args[2], args[3], args[4],args[5]);
			computerDao.updateComputer(computer, Integer.parseInt(args[1]));
		}
		else if(args[0].toLowerCase().equals("--help")) {
			help();
		}
		else {
			System.out.println("You put a wrong argument, please use --help for more details");
		}
	}
	
	public void testArgumentsDeleteShowDetailsByID(int argc, String[] argv) {
		if(argc == 1) {
			System.out.println("You need to put more arguments, see --help");
		}
		else if(argc == 2) {
			//TODO
		}
	}
	
	public void help() {
		System.out.println("SyntaxException : java computer-database sqlRequest [arguments]");
		System.out.println("The sql request and their arguments are :");
		System.out.println("	ListComputer");
		System.out.println("	ListCompany");
		System.out.println("	ShowComputerDetails + ComputerName");
		System.out.println("	ShowComputerDetailsById + ComputerID");
		System.out.println("	DeleteAComputer + ComputerID");
		System.out.println("	CreateAComputer + ComputerName + IntroducedDate + DiscontinuedDate + CompanyID");
		System.out.println("	UpdateAComputer + ComputerID + ComputerName + IntroducedDate + DiscontinuedDate + CompanyID");
	}
	
	public void printComputerDetails(Computer computer) {
		System.out.print(computer.getId() + ", ");
		System.out.print(computer.getName() + ", ");
		System.out.print(computer.getIntroduced() + ", ");
		System.out.print(computer.getDiscontinued() + ", ");
		System.out.println(computer.getCompanyId());
	}
}
