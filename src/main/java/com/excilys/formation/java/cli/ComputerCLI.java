package com.excilys.formation.java.cli;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.java.mapper.MapperComputer;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.service.ComputerService;
import com.excilys.formation.java.validator.Validator;

public class ComputerCLI {
	private MapperComputer mapperComputer = MapperComputer.getInstance();
	private ComputerService computerServices = ComputerService.getInstance();
	private final static Logger LOGGER = LogManager.getLogger(ComputerCLI.class.getName());
	
	/**
	 * Print all the Computer object found in the List<Computer> give by our computerDao
	 */
	public void showComputer() {
		List<Computer> listResults = computerServices.showComputer();
		for(Computer computer : listResults){
			System.out.println(computer.toString());
		}
	}
	
	/**
	 * Print the number of computer give with limit and offset
	 * @param limit
	 * @param offset
	 */
	public void showComputerPage(Page page) {
		List<Computer> listResults = computerServices.showComputerPage(page);
		for(Computer computer : listResults){
			System.out.println(computer.toString());
		}
		System.out.println("Page Number : " + page.getPageNumber());
	}

	/**
	 * Print all the Computer object found with name key
	 * @param name
	 */
	public void showComputerDetails(String name) {
		List<Computer> listResults = computerServices.showComputerDetails(name);
		for(Computer computer : listResults){
			System.out.println(computer.toString());
		}
	}
	
	/**
	 * Print all the Computer object found with id key
	 * @param id
	 */
	public void showComputerDetailsByID(long id) {
		Optional<Computer> optionalComputer = computerServices.showComputerDetailsByID(id);
		if(optionalComputer.isPresent()) {
			Computer computer = optionalComputer.get();
			System.out.println(computer.toString());
		} else {
			LOGGER.info("No computer found with that ID");
		}
	}
	
	/**
	 * Delete a computer with id key
	 * @param id
	 */
	public void deleteComputer(long id) {
		computerServices.deleteComputer(id);
	}
	
	/**
	 * Insert a computer
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param companyID
	 */
	public void insertComputer(String name, String introduced, String discontinued, String companyID) {
		Computer computer = mapperComputer.mapper(name, introduced, discontinued,companyID);
		if(Validator.checkComputer(computer)) {
			computerServices.insertComputer(computer);			
		} else {
			LOGGER.info("COMPUTER NOT CREATED");
		}
	}
	
	/**
	 * Update a computer with id key
	 * @param id
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param companyID
	 */
	public void updateComputer(long id, String name, String introduced, String discontinued, String companyID) {
		Computer computer = mapperComputer.mapper(id, name,introduced,discontinued,companyID);
		if(Validator.checkComputer(computer)) {
			computerServices.updateComputer(computer);
		} else {
			LOGGER.info("COMPUTER NOT UPDATED");
		}
	}
}
