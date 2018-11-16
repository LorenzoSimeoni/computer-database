/**
 * 
 */
package com.excilys.formation.java.cli.view;

import java.util.List;

import com.excilys.formation.java.cli.dao.CompanyDAO;
import com.excilys.formation.java.cli.modele.Company;

/**
 * @author excilys
 *
 */
public class PageCompany {
	private CompanyDAO companyDao = CompanyDAO.getInstance();
	private static int compteur = 0;
	
	private PageCompany(){}
	
	private static PageCompany pageCompany = new PageCompany();
	
	public static PageCompany getInstance() {
		return pageCompany;
	}
	
	public void showCompanyPage(int nbElement) {
		List<Company> listResults = companyDao.listCompany();
		for(int i = compteur, n = listResults.size(), j = i; i < n && i-j<nbElement; i++) { 
	        printCompanyDetails(listResults.get(i));
	        compteur = i;
	    }
		if(compteur == listResults.size()-1) {
			compteur = 0;
		}
	}
	public void printCompanyDetails(Company company) {
		System.out.print(company.getId() + ", ");
		System.out.print(company.getName() + ", ");
	}
}
