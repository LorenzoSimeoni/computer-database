/**
 * 
 */
package com.excilys.formation.java.cli;

import com.excilys.formation.java.service.FeaturesCLI;

/**
 * @author excilys
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FeaturesCLI showComputer = FeaturesCLI.getInstance();

		showComputer.features();		
	}
}

