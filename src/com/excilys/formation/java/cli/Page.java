/**
 * 
 */
package com.excilys.formation.java.cli;

import java.util.Scanner;

public class Page {
	public int limit = 0;
	public int offset = 0;
	
	private Page(){}
	
	private static Page page = new Page();
	
	public static Page getInstance() {
		return page;
	}
	
	public String page(int size, String comp, Scanner sc) {
		CompanyCLI companyCLI = new CompanyCLI();
		ComputerCLI computerCLI = new ComputerCLI();
		String str;
		limit = size;
		if(comp.toLowerCase().equals("company")) {
			companyCLI.showCompanyPage(limit,offset);
		} else if(comp.toLowerCase().equals("computer")) {
			computerCLI.showComputerPage(limit,offset);
		} else {
			return "problem with arg";
		}
		do {
			offset = limit;
			limit = limit + size;
			str = sc.nextLine();
			if(str.toLowerCase().equals("suivant")) {
				if(comp.toLowerCase().equals("company")) {
					companyCLI.showCompanyPage(limit,offset);
				} else if(comp.toLowerCase().equals("computer")) {
					computerCLI.showComputerPage(limit,offset);
				}
			} else if(!str.equals("exit")) {
				System.out.println("Veuillez faire 'suivant' pour en afficher plus, ou quitter avec 'exit' ! ");
			}
		} while(!str.equals("exit"));

		return "exit";
	}
	
	public void pageCompany(int size, Scanner sc) {
		CompanyCLI companyCLI = new CompanyCLI();
		String str;
		limit = size;
		companyCLI.showCompanyPage(limit,offset);
		do {
			offset = limit;
			limit = limit + size;
			str = sc.nextLine();
			if(str.toLowerCase().equals("suivant")) {
				companyCLI.showCompanyPage(limit,offset);
			} else if(!str.equals("exit")) {
				System.out.println("Veuillez faire 'suivant' pour en afficher plus, ou quitter avec 'exit' ! ");
			}
		} while(!str.equals("exit"));
	}
	
	public void pageComputer(int size, Scanner sc) {
		ComputerCLI computerCLI = new ComputerCLI();
		String str;
		limit = size;
		computerCLI.showComputerPage(limit,offset);
		do {
			offset = limit;
			limit = limit + size;
			str = sc.nextLine();
			if(str.toLowerCase().equals("suivant")) {
					computerCLI.showComputerPage(limit,offset);
			} else if(!str.equals("exit")) {
				System.out.println("Veuillez faire 'suivant' pour en afficher plus, ou quitter avec 'exit' ! ");
			}
		} while(!str.equals("exit"));
	}

}
