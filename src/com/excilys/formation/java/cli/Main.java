/**
 * 
 */
package com.excilys.formation.java.cli;

import com.excilys.formation.java.cli.service.FeaturesCLI;

/**
 * @author excilys
 *
 */
public class Main {
	int b;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FeaturesCLI showComputer = FeaturesCLI.getInstance();

		showComputer.features();		
	}
}

