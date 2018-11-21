package com.excilys.formation.java.cli;

import java.util.List;

import com.excilys.formation.java.service.CompanyService;
import com.excilys.formation.java.model.Company;

/**
 * 
 * @author excilys
 *
 */
public class CompanyCLI {
	private CompanyService companyServices = CompanyService.getInstance();

	/**
	 * Print all the Company object found in the List<Company> gived by our companyDao
	 */
	public void showCompany() {
		List<Company> listResults = companyServices.show();
		for(Company company : listResults) {
			System.out.println(company.toString());
		}
	}
	
	public void showCompanyDetailsById(long id) {
		List<Company> listResults = companyServices.showDetailsById(id);
		for(Company company : listResults) {
			System.out.println(company.toString());
		}
	}
	
	public void showCompanyPage(int limit, int offset) {
		List<Company> listResults = companyServices.showPage(limit, offset);
		for(Company company : listResults) {
			System.out.println(company.toString());
		}
	}
}
