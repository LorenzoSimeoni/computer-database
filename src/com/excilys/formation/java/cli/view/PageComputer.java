/**
 * 
 */
package com.excilys.formation.java.cli.view;

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
	
	public void showComputerPage(int nbElement) {
		List<Computer> listResults = computerDao.listComputer();
		for(int i = compteur, n = listResults.size(), j = i; i < n && i-j<nbElement; i++) { 
			printComputerDetails(listResults.get(i));
	        compteur = i;
	    }
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
