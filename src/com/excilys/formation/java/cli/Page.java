/**
 * 
 */
package com.excilys.formation.java.cli;

import java.util.Scanner;

public class Page {
	public int limit = 0;
	public int offset = 0;
	
	public void pageCompany(int size, Scanner sc) {
		sc.nextLine();
		CompanyCLI companyCLI = new CompanyCLI();
		String str;
		offset = size;
		companyCLI.showCompanyPage(limit,offset);
		do {
			limit = limit + size;
			str = sc.nextLine();
			if(str.toLowerCase().equals("suivant")) {
				companyCLI.showCompanyPage(limit,offset);
			} else if(!str.equals("exit") || str.equals("")) {
				System.out.println("Veuillez faire 'enter' ou écrire 'suivant' pour en afficher plus, ou quitter avec 'exit' ! ");
			}
		} while(!str.equals("exit"));
	}
	
	public void pageComputer(int size, Scanner sc) {
		sc.nextLine();
		ComputerCLI computerCLI = new ComputerCLI();
		String str;
		offset = size;
		computerCLI.showComputerPage(limit,offset);
		do {
			limit = limit + size;
			str = sc.nextLine();
			if(str.toLowerCase().equals("suivant") || str.equals("")) {
					computerCLI.showComputerPage(limit,offset);
			} else if(!str.equals("exit")) {
				System.out.println("Veuillez faire 'enter' ou écrire 'suivant' pour en afficher plus, ou quitter avec 'exit' ! ");
			}
		} while(!str.equals("exit"));
	}

}
