package com.excilys.formation.java.service;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.dao.CompanyDAO;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;

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
	
	public Optional<Company> showDetailsById(long id) {
		return companyDao.getDetailsById(id);
	}
	
	public List<Company> showPage(Page page) {
		return companyDao.getListPage(page);
	}
}
