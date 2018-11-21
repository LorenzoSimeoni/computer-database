package com.excilys.formation.java.service;

import java.util.List;

import com.excilys.formation.java.dao.CompanyDAO;
import com.excilys.formation.java.model.Company;

public class CompanyService {
	
	private CompanyDAO companyDao = CompanyDAO.getInstance();
	
	private CompanyService(){}
	
	private static CompanyService companyServices = new CompanyService();
	
	public static CompanyService getInstance() {
		return companyServices;
	}
	
	/**
	 * Print all the Company object found in the List<Company> gived by our companyDao
	 */
	public List<Company> show() {
		return companyDao.getList();
	}
	
	public List<Company> showDetailsById(long id) {
		return companyDao.getDetailsById(id);
	}
	
	public List<Company> showPage(int limit, int offset) {
		return companyDao.getListPage(limit, offset);
	}
}
