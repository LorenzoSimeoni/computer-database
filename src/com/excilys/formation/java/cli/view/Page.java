/**
 * 
 */
package com.excilys.formation.java.cli.view;

import java.util.Scanner;

public class Page {
	
	private Page(){}
	
	private static Page page = new Page();
	
	public static Page getInstance() {
		return page;
	}
	
	public void pagination(int nbElements, String nameOfTable, Scanner sc) {
		String str;
		callPrintFunctions(nameOfTable,nbElements);
		do {
			str = sc.nextLine();
			if(str.toLowerCase().equals("suivant")) {
				callPrintFunctions(nameOfTable,nbElements);
			}
			else {
				System.out.println("Veuillez faire 'suivant' pour en afficher plus, ou quitter avec 'exit' ! ");
			}
		} while(!str.equals("exit"));
	}
	
	public void callPrintFunctions(String nameOfTable, int nbElements) {
		if(nameOfTable.toLowerCase().equals("computer")) {
			PageCompany.getInstance().showCompanyPage(nbElements);
		}
		else if(nameOfTable.toLowerCase().equals("company")) {
			PageComputer.getInstance().showComputerPage(nbElements);
		}
	}

}
