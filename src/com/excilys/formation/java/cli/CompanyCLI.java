package com.excilys.formation.java.cli;

import java.util.List;

import com.excilys.formation.java.dao.CompanyDAO;
import com.excilys.formation.java.model.Company;

/**
 * 
 * @author excilys
 *
 */
public class CompanyCLI {
	
	private CompanyDAO companyDao = CompanyDAO.getInstance();
	
	private CompanyCLI(){}
	
	private static CompanyCLI companyServices = new CompanyCLI();
	
	public static CompanyCLI getInstance() {
		return companyServices;
	}
	
	/**
	 * Print all the Company object found in the List<Company> gived by our companyDao
	 */
	public void showCompany() {
		List<Company> listResults = companyDao.getList();
		for(Company company : listResults) {
			System.out.println(company.toString());
		}
	}
	
	public void showCompanyPage(int limit, int offset) {
		List<Company> listResults = companyDao.getListPage(limit, offset);
		for(Company company : listResults) {
			System.out.println(company.toString());
		}
	}
}
