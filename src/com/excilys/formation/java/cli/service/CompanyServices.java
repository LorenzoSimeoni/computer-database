package com.excilys.formation.java.cli.service;

import java.util.List;

import com.excilys.formation.java.cli.dao.CompanyDAO;
import com.excilys.formation.java.cli.modele.Company;

/**
 * 
 * @author excilys
 *
 */
public class CompanyServices {
	
	private CompanyDAO companyDao = CompanyDAO.getInstance();
	
	private CompanyServices(){}
	
	private static CompanyServices companyServices = new CompanyServices();
	
	public static CompanyServices getInstance() {
		return companyServices;
	}
	
	/**
	 * Print all the Company object found in the List<Company> gived by our companyDao
	 */
	public void showCompany() {
		List<Company> listResults = companyDao.listCompany();
		for(Company company : listResults) {
			printCompanyDetails(company);
		}
	}
	
	/**
	 * Layout for company object
	 * @param company
	 */
	public void printCompanyDetails(Company company) {
		System.out.print(company.getId() + ", ");
		System.out.print(company.getName() + ", ");
	}
}
