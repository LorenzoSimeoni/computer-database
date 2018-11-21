package com.excilys.formation.java.cli;

import java.util.List;

import com.excilys.formation.java.mapper.MapperComputer;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.ComputerService;

public class ComputerCLI {
	
	private ComputerService computerServices = ComputerService.getInstance();
	
	private ComputerCLI(){}
	
	private static ComputerCLI computerCLI = new ComputerCLI();
	
	public static ComputerCLI getInstance() {
		return computerCLI;
	}
	
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
		List<Computer> listResults = computerServices.showComputerDetailsByID(id);
		for(Computer computer : listResults){
			System.out.println(computer.toString());
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
		MapperComputer mapperComputer = MapperComputer.getInstance();
		Computer computer = mapperComputer.mapper(name, introduced,discontinued,companyID);
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
	public void updateComputer(String id, String name, String introduced, String discontinued, String companyID) {
		MapperComputer mapperComputer = MapperComputer.getInstance();
		Computer computer = mapperComputer.mapper(id, name,introduced,discontinued,companyID);
		 computerServices.updateComputer(computer);
	}
}
