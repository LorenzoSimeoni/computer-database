/**
 * 
 */
package com.excilys.formation.java.cli.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.java.cli.dao.CompanyDAO;
import com.excilys.formation.java.cli.dao.ComputerDAO;
import com.excilys.formation.java.cli.mapper.MapperCompany;
import com.excilys.formation.java.cli.mapper.MapperComputer;
import com.excilys.formation.java.cli.modele.Company;
import com.excilys.formation.java.cli.modele.Computer;

public class FeaturesCLI {
	
	public void features(String[] args) throws SQLException {
		if(args.length == 1) {
			if(args[0].toLowerCase().equals("listcomputer")) {
				ComputerDAO computerDao = new ComputerDAO();
				MapperComputer mapperComputer = new MapperComputer();
				ResultSet results = computerDao.listComputer();
				
//				while(results.next()) {
//					 Computer computer = mapperComputer.mapperComputer(results);
//					 System.out.println(computer.getName());
//				}
			}
			if(args[0].toLowerCase().equals("listcompany")) {
				CompanyDAO companyDao = new CompanyDAO();
				ResultSet results = companyDao.listCompany();
				MapperCompany mapperCompany = new MapperCompany();
				
				while(results.next()) {
					 Company company = mapperCompany.mapperCompany(results);
					 System.out.println(company.getName());
				}
			}
		}
	}
}
