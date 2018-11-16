/**
 * 
 */
package com.excilys.formation.java.cli.service;

import java.util.List;

import com.excilys.formation.java.cli.dao.ComputerDAO;
import com.excilys.formation.java.cli.modele.Computer;

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
		List<Computer> listResults = computerDao.listComputer();
		return listResults;
	}
	
	public void showComputerPage(int nbElement, List<Computer> listResults) {
		for(int i = compteur, n = listResults.size(), j = i; i < n && i-j<nbElement; i++) { 
			printComputerDetails(listResults.get(i));
	        compteur = i;
	    }
		compteur++;
		if(compteur == listResults.size()-1) {
			compteur = 0;
		}
	}
	public void printComputerDetails(Computer computer) {
		System.out.print(computer.getId() + ", ");
		System.out.print(computer.getName() + ", ");
		System.out.print(computer.getIntroduced() + ", ");
		System.out.print(computer.getDiscontinued() + ", ");
		System.out.println(computer.getCompanyId());
	}
}
