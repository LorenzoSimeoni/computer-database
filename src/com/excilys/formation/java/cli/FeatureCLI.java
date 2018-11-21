package com.excilys.formation.java.cli;

import java.util.Scanner;
import com.excilys.formation.java.validator.*;

public class FeatureCLI {
	
	private static Scanner sc;
	static CompanyCLI companyCLI = new CompanyCLI();
	static ComputerCLI computerCLI = new ComputerCLI();
	
	public void features() {
		int key;
		boolean quitter = true;
		while(quitter) {
			displayMenu();
			sc = new Scanner(System.in);
			key = validator.userGiveAnInt(sc);
			switch (key) {
			case 1:
				parseShow(sc);
				System.out.println("\n \n ********************************************************* \n \n");
				break;
			case 2:
				parseCreate(sc);
				System.out.println("\n \n ********************************************************* \n \n");
				break;
			case 3:
				parseUpdate(sc);
				System.out.println("\n \n ********************************************************* \n \n");
				break;
			case 4:
				parseDelete(sc);
				System.out.println("\n \n ********************************************************* \n \n");
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
		key = validator.userGiveAnInt(sc);
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
		long id = validator.userGiveALong(sc);
		if(id != -1) {
			computerCLI.showComputerDetailsByID(id);
		}
	}
	public void displayComputerPage(Scanner sc) {
		System.out.println("YOU CHOOSE TO PRINT COMPUTERS WITH PAGING, GIVE THE NUMBER OF ELEMENT ON A PAGE PLEASE");
		int size = validator.userGiveAnInt(sc);
		if(size != -1) {
			Page page = new Page();
			page.pageComputer(size, sc);	
		}
	}
	public void displayCompanyPage(Scanner sc) {
		System.out.println("YOU CHOOSE TO PRINT COMPANIES WITH PAGING, GIVE THE NUMBER OF ELEMENT ON A PAGE PLEASE");
		int size = validator.userGiveAnInt(sc);
		if(size != -1) {
			Page page = new Page();
			page.pageCompany(size, sc);			
		}
	}
	
	public void parseCreate(Scanner sc) {
		displayCreateMenu();
		System.out.println("Give a computer Name");
		sc.nextLine();
		String name = sc.nextLine();
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
		System.out.println("YOU CHOOSE TO CREATE A NEW COMPUTER");
		System.out.println("GIVE AND ID");
		long id = validator.userGiveALong(sc);
		if(id != -1) {
			computerCLI.deleteComputer(id);
		}
	}
	
	public void parseUpdate(Scanner sc) {
		displayUpdateMenu();
		System.out.println("Give a computer ID");
		long id = validator.userGiveALong(sc);
		if(id != -1) {
			computerCLI.showComputerDetailsByID(id);
			System.out.println("Give a computer Name (if you don't want to change the name do an enter)");
			sc.nextLine();
			String name = sc.nextLine();
			if(name.equals("null")) {
				name = null;
			} else if(name.equals("")) {
				name = "nochange";
			}
			System.out.println("Give an introduced Date (if you don't want to fill a date do an enter)");
			String introduced = sc.nextLine();
			if(introduced.equals("null")) {
				introduced = null;
			} else if(name.equals("")) {
				introduced = "nochange";
			}
			System.out.println("Give a discontinued date (if you don't want to fill a date do an enter)");
			String discontinued = sc.nextLine();
			if(discontinued.equals("null")) {
				discontinued = null;
			} else if(name.equals("")) {
				discontinued = "nochange";
			}
			
			System.out.println("Give a Company ID (if you don't want, write null or do an enter)");
			String companyId = sc.nextLine();
			if(companyId.equals("null")) {
				companyId = null;
			} else if(name.equals("")) {
				companyId = "nochange";
			}
			computerCLI.updateComputer(id, name, introduced, discontinued, companyId);
		}
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
}
