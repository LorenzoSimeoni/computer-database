package com.excilys.formation.java.cli;

import java.util.List;

import com.excilys.formation.java.dao.ComputerDAO;
import com.excilys.formation.java.mapper.MapperComputer;
import com.excilys.formation.java.model.Computer;

public class ComputerCLI {
	
	private ComputerDAO computerDao = ComputerDAO.getInstance();
	
	private ComputerCLI(){}
	
	private static ComputerCLI computerServices = new ComputerCLI();
	
	public static ComputerCLI getInstance() {
		return computerServices;
	}
	
	/**
	 * Print all the Computer object found in the List<Computer> give by our computerDao
	 */
	public void showComputer() {
		List<Computer> listResults = computerDao.getList();
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
		List<Computer> listResults = computerDao.getListPage(limit, offset);
		for(Computer computer : listResults){
			System.out.println(computer.toString());
		}
	}

	/**
	 * Print all the Computer object found with name key
	 * @param name
	 */
	public void showComputerDetails(String name) {
		List<Computer> listResults = computerDao.getDetailsByName(name);
		for(Computer computer : listResults){
			System.out.println(computer.toString());
		}
	}
	
	/**
	 * Print all the Computer object found with id key
	 * @param id
	 */
	public void showComputerDetailsByID(int id) {
		List<Computer> listResults = computerDao.getDetailsByID(id);
		for(Computer computer : listResults){
			System.out.println(computer.toString());
		}
	}
	
	/**
	 * Delete a computer with id key
	 * @param id
	 */
	public void deleteComputer(int id) {
		computerDao.delete(id);
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
		computerDao.create(computer);
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
		computerDao.update(computer);
	}
}
