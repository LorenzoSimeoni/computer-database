package com.excilys.formation.cli;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.formation.checker.Controller;
import com.excilys.formation.configSpring.SpringRootConfig;
import com.excilys.formation.model.Page;

public class FeatureCLI {
	private static Scanner sc;
	static ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringRootConfig.class);
	static CompanyCLI companyCLI = applicationContext.getBean("companyCLI", CompanyCLI.class);
	static ComputerCLI computerCLI = applicationContext.getBean("computerCLI", ComputerCLI.class);
	
	public void features() {
		int key;
		boolean quitter = true;
		while(quitter) {
			displayMenu();
			sc = new Scanner(System.in);
			key = Controller.userGiveAnInt(sc);
			switch (key) {
			case 1:
				parseShow(sc);
				System.out.println("\n   \n ************************************************************************************************************** \n \n");
				break;
			case 2:
				parseCreate(sc);
				System.out.println("\n \n ************************************************************************************************************** \n \n");
				break;
			case 3:
				parseUpdate(sc);
				System.out.println("\n \n ************************************************************************************************************** \n \n");
				break;
			case 4:
				parseDelete(sc);
				System.out.println("\n \n ************************************************************************************************************** \n \n");
				break;
			case 5:
				quitter = false;
				break;
			default:
				break;
			}
		}
	}
	
	public void parseShow(Scanner sc) {
		int key = 0;
		displayShowMenu();
		key = Controller.userGiveAnInt(sc);
		switch (key) {
			case 1:
				computerCLI.showComputer();
				break;
			case 2:
				companyCLI.showCompany();
				break;
			case 3:
				displayComputerDetailsName(sc);
				break;
			case 4:
				displayComputerDetailsID(sc);
				break;
			case 5:
				displayComputerPage(sc);
				break;
			case 6:
				displayCompanyPage(sc);
				break;
			default:
				break;			
		}
	}
	
	public void displayComputerDetailsName(Scanner sc) {
		System.out.println("YOU CHOOSE TO PRINT COMPUTER DETAILS BY NAME, GIVE A NAME PLEASE");
		sc.nextLine();
		String name = sc.nextLine();
		computerCLI.showComputerDetails(name);
	}
	public void displayComputerDetailsID(Scanner sc) {
		System.out.println("YOU CHOOSE TO PRINT COMPUTER DETAILS BY ID, GIVE AN ID PLEASE");
		long id = Controller.userGiveALong(sc);
		if(id != -1) {
			computerCLI.showComputerDetailsByID(id);
		}
	}
	public void displayComputerPage(Scanner sc) {
		System.out.println("YOU CHOOSE TO PRINT COMPUTERS WITH PAGING, GIVE THE NUMBER OF ELEMENT ON A PAGE PLEASE");
		int size = Controller.userGiveAnInt(sc);
		if(size != -1) {
			pageComputer(size, sc);	
		}
	}
	public void displayCompanyPage(Scanner sc) {
		System.out.println("YOU CHOOSE TO PRINT COMPANIES WITH PAGING, GIVE THE NUMBER OF ELEMENT ON A PAGE PLEASE");
		int size = Controller.userGiveAnInt(sc);
		if(size != -1) {
			pageCompany(size, sc);			
		}
	}
	
	public void parseCreate(Scanner sc) {
		displayCreateMenu();
		System.out.println("Give a computer Name");
		sc.nextLine();
		String name = sc.nextLine();
		if(name.equals("null")|| name.equals("")) {
			name = null;
		}
		System.out.println("Give an introduced Date (if you don't want to fill a date do an enter)");
		String introduced = sc.nextLine();
		String discontinued = null;
		if(introduced.equals("null") || introduced.equals("")) {
			introduced = null;
		} else {
			System.out.println("Give a discontinued date (if you don't want to fill a date do an enter)");
			discontinued = sc.nextLine();
			if(discontinued.equals("null") || discontinued.equals("")) {
				discontinued = null;
			} 
		}
		System.out.println("Give a Company ID (if you don't want, write null or do an enter)");
		String companyId = sc.nextLine();
		if(companyId.equals("null") || companyId.equals("")) {
			companyId = null;
		}
		computerCLI.insertComputer(name, introduced, discontinued, companyId);
	}
	
	public void parseDelete(Scanner sc) {
		long id = 0;
		int key = 0;
		displayDeleteMenu();
		key = Controller.userGiveAnInt(sc);
		switch (key) {
			case 1:
				System.out.println("YOU CHOOSE TO DELETE A COMPUTER");
				System.out.println("GIVE AND ID");
				
				id = Controller.userGiveALong(sc);
				if(id != -1) {
					computerCLI.deleteComputer(id);
				}
				break;
			case 2:
				System.out.println("YOU CHOOSE TO DELETE A COMPANY");
				System.out.println("GIVE AND ID");
				id = Controller.userGiveALong(sc);
				if(id != -1) {
					companyCLI.deleteCompany(id);
				}
				break;
		}
	}
	
	public void parseUpdate(Scanner sc) {
		displayUpdateMenu();
		System.out.println("Give a computer ID");
		long id = Controller.userGiveALong(sc);
		if(id != -1) {
			computerCLI.showComputerDetailsByID(id);
			System.out.println("Give a computer Name (if you don't want to change the name do an enter)");
			sc.nextLine();
			String name = sc.nextLine();
			System.out.println("Give an introduced Date (if you don't want to fill a date do an enter)");
			String introduced = sc.nextLine();
			if(introduced.equals("null")) {
				introduced = null;
			}
			System.out.println("Give a discontinued date (if you don't want to fill a date do an enter)");
			String discontinued = sc.nextLine();
			if(discontinued.equals("null")) {
				discontinued = null;
			}
			System.out.println("Give a Company ID (if you don't want, write null or do an enter)");
			String companyId = sc.nextLine();
			if(companyId.equals("null")) {
				companyId = null;
			}
			computerCLI.updateComputer(id, name, introduced, discontinued, companyId);
		}
	}
	
	public void pageCompany(int size, Scanner sc) {
		Page page = new Page();		
		sc.nextLine();
		String str;
		page.setOffset(size);
		do {
			displayPageMenu();
			str = sc.nextLine();
			if(str.toLowerCase().equals("next") || str.equals("")) {
				companyCLI.showCompanyPage(page);
				page.incrementLimit();
			} else if(!str.equals("exit")) {
				if(Controller.testStringIsAInt(str)) {
					page.changePage(Integer.parseInt(str));
					companyCLI.showCompanyPage(page);
				}
			}
		} while(!str.equals("exit"));
	}
	
	public void pageComputer(int size, Scanner sc) {
		Page page = new Page();
		sc.nextLine();
		String str;
		page.setOffset(size);
		page.incrementLimit();
		computerCLI.showComputerPage(page);
		do {
			displayPageMenu();
			str = sc.nextLine();
			if(str.toLowerCase().equals("next") || str.equals("")) {
				page.incrementLimit();
				computerCLI.showComputerPage(page);
			} else if(!str.equals("exit")) {
				if(Controller.testStringIsAInt(str)) {
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
	
	public void displayMenu() {
		System.out.println("WELCOME IN THE COMPUTER DATABASE APPLICATION");
		System.out.println("MENU : ");
		System.out.println("          1. SHOW");
		System.out.println("          2. CREATE");
		System.out.println("          3. UPDATE");
		System.out.println("          4. DELETE");
		System.out.println("          5. EXIT");
	}
	
	public void displayShowMenu() {
		System.out.println("WHAT DO YOU WANT TO SHOW ?");
		System.out.println("CHOICES : ");
		System.out.println("          1. COMPUTERS");
		System.out.println("          2. COMPANIES");
		System.out.println("          3. COMPUTER DETAILS BY NAME");
		System.out.println("          4. COMPUTER DETAILS BY ID");
		System.out.println("          5. COMPUTERS WITH PAGING");
		System.out.println("          6. COMPANIES WITH PAGING");
	}
	
	public void displayCreateMenu() {
		System.out.println("YOU CHOOSE TO CREATE A NEW COMPUTER");
		System.out.println("Give fields when asked, if you want to exit write 'exit'");
	}
	
	public void displayUpdateMenu() {
		System.out.println("YOU CHOOSE TO UPDATE A NEW COMPUTER");
		System.out.println("Give fields when asked, if you want to exit write 'exit'");
	}
	
	public void displayDeleteMenu() {
		System.out.println("WHAT DO YOU WANT TO DELETE ?");
		System.out.println("          1. COMPUTER");
		System.out.println("          2. COMPANY");
	}
}
