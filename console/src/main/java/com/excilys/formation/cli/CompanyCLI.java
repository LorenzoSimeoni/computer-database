package com.excilys.formation.cli;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.service.CompanyService;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Page;

/**
 * 
 * @author excilys
 *
 */
@Component
public class CompanyCLI {
	
	@Autowired
	private CompanyService companyServices;

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
	
	public void deleteCompany(long id) {
		companyServices.delete(id);
	}
}
