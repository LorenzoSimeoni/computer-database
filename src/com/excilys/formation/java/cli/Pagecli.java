/**
 * 
 */
package com.excilys.formation.java.cli;

import java.util.Scanner;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.validator.Validator;

public class Pagecli {
	private Page page = new Page();
	
	public void pageCompany(int size, Scanner sc) {
		
		page.incrementLimit();
		
		sc.nextLine();
		CompanyCLI companyCLI = new CompanyCLI();
		String str;
		page.setOffset(size);
		do {
			displayPageMenu();
			str = sc.nextLine();
			if(str.toLowerCase().equals("next") || str.equals("")) {
				companyCLI.showCompanyPage(page);
				page.incrementLimit();
			} else if(!str.equals("exit")) {
				if(Validator.testStringIsAInt(str)) {
					page.changePage(Integer.parseInt(str));
					companyCLI.showCompanyPage(page);
				}
			}
		} while(!str.equals("exit"));
	}
	
	public void pageComputer(int size, Scanner sc) {
		sc.nextLine();
		ComputerCLI computerCLI = new ComputerCLI();
		String str;
		page.setOffset(size);
		do {
			displayPageMenu();
			str = sc.nextLine();
			if(str.toLowerCase().equals("next") || str.equals("")) {
				page.incrementLimit();
				computerCLI.showComputerPage(page);
			} else if(!str.equals("exit")) {
				if(Validator.testStringIsAInt(str)) {
					page.changePage(Integer.parseInt(str));
					computerCLI.showComputerPage(page);
				}
			}
		} while(!str.equals("exit"));
	}
	
	public void displayPageMenu() {
		System.out.println("\n\n\n\nMENU :");
		System.out.println("           SHOW THE NEXT PAGE : enter , or write 'next' ");
		System.out.println("                  CHANGE PAGE : write a number");
		System.out.println("                      TO QUIT : write 'exit'");
	}

}
