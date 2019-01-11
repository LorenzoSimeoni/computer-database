/**
 * 
 */
package com.excilys.formation.cli;

import com.excilys.formation.model.Computer;

/**
 * @author excilys
 *
 */

public class Main {
	/**
	 * @param args
	 */
	static int aStatistic = 1;
	public static void main(String []args) {
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		System.out.println(passwordEncoder.encode("mdp"));
//		FeatureCLI aCli = new FeatureCLI();
//		aCli.features();
		
		EntryPoint entryPoint = new EntryPoint();
		Computer computer = entryPoint.getJsonEmployee();
		System.out.println(computer.toString());
	}
}

