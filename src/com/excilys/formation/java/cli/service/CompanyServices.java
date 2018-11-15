package com.excilys.formation.java.cli.service;

import java.util.List;

import com.excilys.formation.java.cli.dao.CompanyDAO;
import com.excilys.formation.java.cli.modele.Company;

public class CompanyServices {
	
	CompanyDAO companyDao = CompanyDAO.getInstance();
	
	private CompanyServices(){}
	
	private static CompanyServices companyServices = new CompanyServices();
	
	public static CompanyServices getInstance() {
		return companyServices;
	}
	
	public void showCompany() {
		List<Company> listResults = companyDao.listCompany();
		for(Company result : listResults) {
			System.out.println(result.getName());
		}
	}
}
