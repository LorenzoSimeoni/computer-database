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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FeaturesCLI showComputer = FeaturesCLI.getInstance();
		
		showComputer.features();

		//String[] arguments = {"show","computerdetailsbyid","543"};
		//String[] arguments = {"show","computer"};
		//String[] arguments = {"delete","computer","579"};
		//String[] arguments = {"insert","computer","abcdefg","2zgrgs",null,"0"};
		//String[] arguments = {"insert","computer","abcdefg","2018-10-10T00:00:00","2017-10-10T00:00:00","0"};
		//String[] arguments = {"insert","computer","zfzefzf",null,null,null};
		//String[] arguments = {"insert","computer","580","azerty",null,null,null};
		//String[] arguments = {"update","computer","595","azerty",null,null,null};
		//String[] arguments = {"--help"};
		
	}
}
