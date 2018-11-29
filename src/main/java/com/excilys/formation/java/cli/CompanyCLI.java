package com.excilys.formation.java.cli;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.java.service.CompanyService;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;

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
		Optional<Company> optionalCompany = companyServices.showDetailsById(id);
		if(optionalCompany.isPresent()) {
			Company company = optionalCompany.get();
			System.out.println(company.toString());
		} else {
			System.out.println("No companies found");
		}
	}
	
	public void showCompanyPage(Page page) {
		List<Company> listResults = companyServices.showPage(page);
		for(Company company : listResults) {
			System.out.println(company.toString());
		}
		System.out.println("Page Number : " + page.getPageNumber());
	}
}
