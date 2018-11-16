/**
 * 
 */
package com.excilys.formation.java.cli.service;

import java.util.List;
import java.util.Scanner;

import com.excilys.formation.java.cli.modele.Company;
import com.excilys.formation.java.cli.modele.Computer;

public class Page {
	
	private List<Company> listResultsCompany = null;
	private List<Computer> listResultsComputer = null;
	private boolean listEnd = false;
	
	private Page(){}
	
	private static Page page = new Page();
	
	public static Page getInstance() {
		return page;
	}
	
	public String pagination(int nbElements, String nameOfTable, Scanner sc) {
		String str;

		if(nameOfTable.toLowerCase().equals("company")) {
			listResultsCompany = PageCompany.getInstance().initiateTable();
		} else if(nameOfTable.toLowerCase().equals("computer")) {
			listResultsComputer = PageComputer.getInstance().initiateTable();
		}
		callPrintFunctions(nameOfTable,nbElements);
		do {
			str = sc.nextLine();
			if(str.toLowerCase().equals("suivant")) {
				callPrintFunctions(nameOfTable,nbElements);
			}
			else if(!str.equals("exit")) {
				System.out.println("Veuillez faire 'suivant' pour en afficher plus, ou quitter avec 'exit' ! ");
			}
		} while(!str.equals("exit") && !listEnd);
		return str;
	}
	
	public void callPrintFunctions(String nameOfTable, int nbElements) {
		if(nameOfTable.toLowerCase().equals("company")) {
			listEnd = PageCompany.getInstance().showCompanyPage(nbElements,listResultsCompany);
		}
		else if(nameOfTable.toLowerCase().equals("computer")) {
			listEnd = PageComputer.getInstance().showComputerPage(nbElements,listResultsComputer);
		}
	}

}
