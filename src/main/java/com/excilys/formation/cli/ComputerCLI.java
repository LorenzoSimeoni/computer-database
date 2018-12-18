package com.excilys.formation.cli;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.exception.NotPermittedComputerException;
import com.excilys.formation.mapper.MapperComputer;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Page;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.validator.Validator;

@Component
public class ComputerCLI {
	@Autowired
	private MapperComputer mapperComputer;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private Validator validator;
	private final static Logger LOGGER = LogManager.getLogger(ComputerCLI.class.getName());
	
	/**
	 * Print all the Computer object found in the List<Computer> give by our computerDao
	 */
	public void showComputer() {
		List<Computer> listResults = computerService.showComputer();
		
		if (listResults.isEmpty()) {
			System.out.println("oplplplp");
		} else {
			for(Computer computer : listResults){
				System.out.println(computer.toString());
			}
			
		}
	}
	
	/**
	 * Print the number of computer give with limit and offset
	 * @param limit
	 * @param offset
	 */
	public void showComputerPage(Page page) {
		List<Computer> listResults = computerService.showComputerPage(page);
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
		List<Computer> listResults = computerService.showComputerDetails(name);
		for(Computer computer : listResults){
			System.out.println(computer.toString());
		}
	}
	
	/**
	 * Print all the Computer object found with id key
	 * @param id
	 */
	public void showComputerDetailsByID(long id) {
		Optional<Computer> optionalComputer = computerService.showComputerDetailsByID(id);
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
		computerService.deleteComputer(id);
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
		try {
			validator.checkComputer(computer);
			computerService.insertComputer(computer);
		} catch (NotPermittedComputerException e) {
			LOGGER.info(" COMPUTER NOT CREATED "+e.getErrorMsg(),e);
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
		try {
			validator.checkComputer(computer);
			computerService.updateComputer(computer);							
		} catch (NotPermittedComputerException e) {
			LOGGER.info(" COMPUTER NOT UPDATED "+e.getErrorMsg(),e);
		}	
	}
}
