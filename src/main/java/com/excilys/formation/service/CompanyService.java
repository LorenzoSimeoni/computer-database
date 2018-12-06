package com.excilys.formation.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.excilys.formation.dao.CompanyDAO;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Page;

public class CompanyService {
	
	private CompanyDAO companyDao = CompanyDAO.getInstance();
	private ComputerService computerService = ComputerService.getInstance();

	
	private CompanyService(){}
	
	private static CompanyService companyServices = new CompanyService();
	
	public static CompanyService getInstance() {
		return companyServices;
	}
	
	/**
	 * Fill a list with all the Company object found in the List<Company> gived by our companyDao
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
	
	public int delete(long id) {
		List<Computer> list = computerService.showComputerDetailsByCompanyID(id);
		Stream<Computer> sp = list.stream();
		sp.forEach(i -> computerService.deleteComputer(i.getId()));
		return companyDao.delete(id);
	}
}
