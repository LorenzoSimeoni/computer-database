package com.excilys.formation.java.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.service.CompanyService;

public class Validator {
	private static final Logger LOGGER = Logger.getLogger( Validator.class.getName() );
	
	public static int userGiveAnInt(Scanner sc) {
		int userInt = -1;
		try {
			userInt = sc.nextInt();
		} catch (InputMismatchException e) {
			LOGGER.log(Level.FINE, "You have to give an Integer but you put something else");
		}			
		return userInt;
	}
	
	public static long userGiveALong(Scanner sc) {
		long userLong = -1;
		try {
			userLong = sc.nextInt();
		} catch (InputMismatchException e) {
			//TODO LOGGER
		}			
		return userLong;
	}
	
	public static boolean testStringIsAInt(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static boolean testStringIsALong(String str) {
		try {
			Long.parseLong(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean testStringIsADate(String str) {
		try {
			LocalDateTime.parse(str);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}
	
	public static boolean companyIdExist(long id) {
		CompanyService companyServices = CompanyService.getInstance();
		List<Company> listResults = companyServices.showDetailsById(id);
		if(listResults.isEmpty()) {
			return false;
		}
		return true;
	}
}
