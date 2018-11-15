/**
 * 
 */
package com.excilys.formation.java.cli.service;

public class FeaturesCLI {
	
	private FeaturesCLI(){}
	
	private static FeaturesCLI featuresCLI = new FeaturesCLI();
	
	public static FeaturesCLI getInstance() {
		return featuresCLI;
	}
	
	public void features(String[] args) {
		if(args[0].toLowerCase().equals("show")) {
			if (args[1].toLowerCase().equals("computer")) {
				ComputerServices.getInstance().showComputer();
			}
			else if (args[1].toLowerCase().equals("company")) {
				CompanyServices.getInstance().showCompany();
			}
			else if(args[1].toLowerCase().equals("computerdetails")) {
				ComputerServices.getInstance().showComputerDetails(args[2]);
			}
			else if(args[1].toLowerCase().equals("computerdetailsbyid")) {
				ComputerServices.getInstance().showComputerDetailsByID(Integer.parseInt(args[2]));
			}
			else {
				problemWithArgument();
			}
		}
		else if(args[0].toLowerCase().equals("insert")) {
			if(args[1].toLowerCase().equals("computer")) {
				ComputerServices.getInstance().insertComputer(args[2], args[3], args[4], args[5]);
			}
			else {
				problemWithArgument();
			}
		}
		else if (args[0].toLowerCase().equals("delete")) {
			if(args[1].toLowerCase().equals("computer")) {
				ComputerServices.getInstance().deleteComputer(Integer.parseInt(args[1]));
			}
			else {
				problemWithArgument();
			}
		}
		else if (args[0].toLowerCase().equals("update")) {
			if(args[1].toLowerCase().equals("computer")) {
				ComputerServices.getInstance().updateComputer(args[2], args[3], args[4], args[5],args[6]);
			}
			else {
				problemWithArgument();
			}
		}
		else if(args[0].toLowerCase().equals("--help")) {
			helper();
		}
		else {
			problemWithArgument();
		}
	}
	
	public void testArgumentsDeleteShowDetailsByID(int argc, String[] argv) {
		if(argc == 1) {
			System.out.println("You need to put more arguments, see --help");
		}
		else if(argc == 2) {
			//TODO
		}
	}
	
	public void helper() {
		System.out.println("SyntaxException : java computer-database sqlRequest [arguments]");
		System.out.println("The sql request and their arguments are :");
		System.out.println("	Show + Computer");
		System.out.println("	Show + Company");
		System.out.println("	Show + ComputerDetails + ComputerName");
		System.out.println("	Show + ComputerDetailsById + ComputerID");
		System.out.println("	Delete + Computer + ComputerID");
		System.out.println("	Insert + Computer + ComputerName + IntroducedDate + DiscontinuedDate + CompanyID");
		System.out.println("	Update + Computer + ComputerID + ComputerName + IntroducedDate + DiscontinuedDate + CompanyID");
	}
	
	public void problemWithArgument() {
		System.out.println("You put a wrong argument, please use --help for more details");
	}
	

}
