/**
 * 
 */
package com.excilys.formation.java.cli;

import java.sql.SQLException;
import java.text.ParseException;

import com.excilys.formation.java.cli.service.FeaturesCLI;

/**
 * @author excilys
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws SQLException, ParseException {
		FeaturesCLI showComputer = FeaturesCLI.getInstance();
		
		//String[] arguments = {"showcomputerdetailsbyid","543"};
		String[] arguments = {"showcomputer"};
		//String[] arguments = {"deletecomputer","578"};
		//String[] arguments = {"insertcomputer","zfzefzf","2018-10-10T00:00:00","2019-10-10T00:00:00","-1"};
		//String[] arguments = {"insertcomputer","zfzefzf",null,null,null};
		//String[] arguments = {"insertcomputer","580","azerty",null,null,null};
		//String[] arguments = {"--help"};
		showComputer.features(arguments);
	}

}
