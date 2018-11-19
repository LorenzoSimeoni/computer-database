/**
 * 
 */
package com.excilys.formation.java.service;

import java.util.List;

import com.excilys.formation.java.dao.CompanyDAO;
import com.excilys.formation.java.model.Company;

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
	
	public List<Company> initiateTable() {
		List<Company> listResults = companyDao.getList();
		return listResults;
	}
	
	public boolean showCompanyPage(int nbElement, List<Company> listResults) {
		for(int i = compteur, n = listResults.size(), j = i; i < n && i-j<nbElement; i++) { 
	        System.out.println(listResults.get(i).toString());
	        compteur = i;
	    }
		compteur++;
		if(compteur == listResults.size()) {
			compteur = 0;
			return true;
		}
		return false;
	}
}
