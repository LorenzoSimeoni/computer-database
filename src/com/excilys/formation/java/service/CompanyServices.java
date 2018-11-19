package com.excilys.formation.java.service;

import java.util.List;

import com.excilys.formation.java.dao.CompanyDAO;
import com.excilys.formation.java.model.Company;

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
