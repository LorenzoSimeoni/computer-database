/**
 * 
 */
package com.excilys.formation.java.service;

import java.util.List;

import com.excilys.formation.java.dao.ComputerDAO;
import com.excilys.formation.java.model.Computer;

/**
 * @author excilys
 *
 */
public class PageComputer {
	private ComputerDAO computerDao = ComputerDAO.getInstance();
	private static int compteur = 0;
	
	private PageComputer(){}
	
	private static PageComputer pageComputer = new PageComputer();
	
	public static PageComputer getInstance() {
		return pageComputer;
	}
	
	public List<Computer> initiateTable() {
		List<Computer> listResults = computerDao.getList();
		return listResults;
	}
	
	public boolean showComputerPage(int nbElement, List<Computer> listResults) {
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
