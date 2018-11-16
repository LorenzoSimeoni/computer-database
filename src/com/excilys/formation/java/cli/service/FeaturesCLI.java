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
			String[] args = str.split(" ");
			
			if(args.length!=0) {
				if(args[0].toLowerCase().equals("show")) {
					if(args.length > 1) {
						if (args[1].toLowerCase().equals("computer")) {
							if(args.length==3) {
								if (testStringIsAInt(args[2])) {
									str = Page.getInstance().pagination(Integer.parseInt(args[2]), args[1], sc);
								}else problemWithArgument();
							}
							else ComputerServices.getInstance().showComputer();								
						}else if (args[1].toLowerCase().equals("company")) {
							if(args.length==3) {
								if (testStringIsAInt(args[2])) {
									str = Page.getInstance().pagination(Integer.parseInt(args[2]), args[1], sc);
								}else problemWithArgument();
							} else CompanyServices.getInstance().showCompany();
						}else if(args[1].toLowerCase().equals("computerdetails")) {
							if(args.length==3) {
								ComputerServices.getInstance().showComputerDetails(args[2]);								
							}else if(args.length==4) {
								//TODO
							}else problemWithArgument();
						}else if(args[1].toLowerCase().equals("computerdetailsbyid")) {
							if(args.length==3) {
								if (testStringIsAInt(args[2])) {
									ComputerServices.getInstance().showComputerDetailsByID(Integer.parseInt(args[2]));
								}else problemWithArgument();								
							}else if(args.length==4) {
								if (testStringIsAInt(args[3])) {
									//TODO
								}else problemWithArgument();
							}else problemWithArgument();
						}else problemWithArgument();				
					}else problemWithArgument();
				}else if(args[0].toLowerCase().equals("insert")) {
					if(args.length > 1) {
						if(args[1].toLowerCase().equals("computer")) {
							if(args.length==6) {
								if ((testStringIsADate(args[3]) || args[3]==null) && (testStringIsADate(args[4]) || args[4]==null)) {
									ComputerServices.getInstance().insertComputer(args[2], args[3], args[4], args[5]);					
								}else problemWithArgument();								
							}else problemWithArgument();
						}else problemWithArgument();						
					}else problemWithArgument();
				}else if (args[0].toLowerCase().equals("delete")) {
					if(args.length > 1) {
						if(args[1].toLowerCase().equals("computer")) {
							if(args.length==3) {
								if(testStringIsAInt(args[2])) {
									ComputerServices.getInstance().deleteComputer(Integer.parseInt(args[2]));					
								}else problemWithArgument();								
							}else problemWithArgument();
						}else problemWithArgument();						
					}else problemWithArgument();
				}else if (args[0].toLowerCase().equals("update")) {
					if(args.length > 1) {
						if(args[1].toLowerCase().equals("computer")) {
							if(args.length==7) {
								if ((testStringIsADate(args[3]) || args[3]==null) && (testStringIsADate(args[4]) || args[4]==null)) {
									ComputerServices.getInstance().updateComputer(args[2], args[3], args[4], args[5],args[6]);					
								}else problemWithArgument();								
							}else problemWithArgument();
						}else problemWithArgument();						
					}else problemWithArgument();
				}else if(args[0].toLowerCase().equals("--help")) {
					helper();
				}else {
					if(!args[0].toLowerCase().equals("exit")) {
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
