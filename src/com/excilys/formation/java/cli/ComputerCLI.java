package com.excilys.formation.java.cli;

import java.util.List;

import com.excilys.formation.java.mapper.MapperComputer;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.ComputerService;

public class ComputerCLI {
	MapperComputer mapperComputer = MapperComputer.getInstance();
	private ComputerService computerServices = ComputerService.getInstance();
	
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
	public void showComputerPage(int limit, int offset) {
		List<Computer> listResults = computerServices.showComputerPage(limit, offset);
		for(Computer computer : listResults){
			System.out.println(computer.toString());
		}
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
		Computer computer = computerServices.showComputerDetailsByID(id);
		System.out.println(computer.toString());
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
		computerServices.insertComputer(computer);
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
		 computerServices.updateComputer(computer);
	}
}
