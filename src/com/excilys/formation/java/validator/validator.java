package com.excilys.formation.java.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class validator {
	
	public static int userGiveAnInt(Scanner sc) {
		int userInt = -1;
		try {
			userInt = sc.nextInt();
		} catch (InputMismatchException e) {
			//TODO LOGGER
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

	public static boolean testStringIsADate(String str) {
		try {
			LocalDateTime.parse(str);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}
	
	public static boolean companyIDExist(long companyId) {
		
		return false;
	}
}
