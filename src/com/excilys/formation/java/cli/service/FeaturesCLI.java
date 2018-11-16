/**
 * 
 */
package com.excilys.formation.java.cli.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class FeaturesCLI {
	
	private FeaturesCLI(){}
	
	private static FeaturesCLI featuresCLI = new FeaturesCLI();
	
	public static FeaturesCLI getInstance() {
		return featuresCLI;
	}
	
	public void features() {
		Scanner sc = new Scanner(System.in);
		String str;
		
		do {
			str = sc.nextLine();
			String[] userEntry = str.split(" ");
			
			if(userEntry.length!=0) {
				if(userEntry[0].toLowerCase().equals("show")) {
					if(userEntry.length > 1) {
						if (userEntry[1].toLowerCase().equals("computer")) {
							if(userEntry.length==3) {
								if (testStringIsAInt(userEntry[2])) {
									str = Page.getInstance().pagination(Integer.parseInt(userEntry[2]), userEntry[1], sc);
								}else problemWithArgument();
							}
							else ComputerServices.getInstance().showComputer();								
						}else if (userEntry[1].toLowerCase().equals("company")) {
							if(userEntry.length==3) {
								if (testStringIsAInt(userEntry[2])) {
									str = Page.getInstance().pagination(Integer.parseInt(userEntry[2]), userEntry[1], sc);
								}else problemWithArgument();
							} else CompanyServices.getInstance().showCompany();
						}else if(userEntry[1].toLowerCase().equals("computerdetails")) {
							if(userEntry.length==3) {
								ComputerServices.getInstance().showComputerDetails(userEntry[2]);								
							}else if(userEntry.length==4) {
								//TODO
							}else problemWithArgument();
						}else if(userEntry[1].toLowerCase().equals("computerdetailsbyid")) {
							if(userEntry.length==3) {
								if (testStringIsAInt(userEntry[2])) {
									ComputerServices.getInstance().showComputerDetailsByID(Integer.parseInt(userEntry[2]));
								}else problemWithArgument();								
							}else if(userEntry.length==4) {
								if (testStringIsAInt(userEntry[3])) {
									//TODO
								}else problemWithArgument();
							}else problemWithArgument();
						}else problemWithArgument();				
					}else problemWithArgument();
				}else if(userEntry[0].toLowerCase().equals("insert")) {
					if(userEntry.length > 1) {
						if(userEntry[1].toLowerCase().equals("computer")) {
							if(userEntry.length==6) {
								if ((testStringIsADate(userEntry[3]) || userEntry[3]==null) && (testStringIsADate(userEntry[4]) || userEntry[4]==null)) {
									ComputerServices.getInstance().insertComputer(userEntry[2], userEntry[3], userEntry[4], userEntry[5]);					
								}else problemWithArgument();								
							}else problemWithArgument();
						}else problemWithArgument();						
					}else problemWithArgument();
				}else if (userEntry[0].toLowerCase().equals("delete")) {
					if(userEntry.length > 1) {
						if(userEntry[1].toLowerCase().equals("computer")) {
							if(userEntry.length==3) {
								if(testStringIsAInt(userEntry[2])) {
									ComputerServices.getInstance().deleteComputer(Integer.parseInt(userEntry[2]));					
								}else problemWithArgument();								
							}else problemWithArgument();
						}else problemWithArgument();						
					}else problemWithArgument();
				}else if (userEntry[0].toLowerCase().equals("update")) {
					if(userEntry.length > 1) {
						if(userEntry[1].toLowerCase().equals("computer")) {
							if(userEntry.length==7) {
								if ((testStringIsADate(userEntry[3]) || userEntry[3]==null) && (testStringIsADate(userEntry[4]) || userEntry[4]==null)) {
									ComputerServices.getInstance().updateComputer(userEntry[2], userEntry[3], userEntry[4], userEntry[5],userEntry[6]);					
								}else problemWithArgument();								
							}else problemWithArgument();
						}else problemWithArgument();						
					}else problemWithArgument();
				}else if(userEntry[0].toLowerCase().equals("--help")) {
					helper();
				}else {
					if(!userEntry[0].toLowerCase().equals("exit")) {
						problemWithArgument();
					}
				}
			}
			else problemWithArgument();
		} while(!str.equals("exit"));
		sc.close();
	}
	
	public boolean testStringIsAInt(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public boolean testStringIsADate(String str) {
		try {
			LocalDateTime.parse(str);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}
	
	public void helper() {
		System.out.println("Syntax : java computer-database sqlRequest [arguments]");
		System.out.println("The sql request and their arguments are :");
		System.out.println("	Show Computer");
		System.out.println("	Show Company");
		System.out.println("	Show Computer nbElements");
		System.out.println("	Show Company nbElement");
		System.out.println("	Show ComputerDetails ComputerName");
		System.out.println("	Show ComputerDetails ComputerName number #if you want pagination");
		System.out.println("	Show ComputerDetailsById ComputerID");
		System.out.println("	Show ComputerDetailsById ComputerID      #if you want pagination");
		System.out.println("	Delete Computer ComputerID");
		System.out.println("	Insert Computer ComputerName IntroducedDate DiscontinuedDate CompanyID");
		System.out.println("	Update Computer ComputerID ComputerName IntroducedDate DiscontinuedDate CompanyID");
	}
	
	public void problemWithArgument() {
		System.out.println("You put a wrong argument, please use --help for more details");
	}
	

}
