package com.excilys.formation.java.cli.service;

import java.util.List;

import com.excilys.formation.java.cli.dao.ComputerDAO;
import com.excilys.formation.java.cli.mapper.MapperComputer;
import com.excilys.formation.java.cli.modele.Computer;

public class ComputerServices {
	
	private ComputerDAO computerDao = ComputerDAO.getInstance();
	
	private ComputerServices(){}
	
	private static ComputerServices computerServices = new ComputerServices();
	
	public static ComputerServices getInstance() {
		return computerServices;
	}
	
	public void showComputer() {
		List<Computer> listResults = computerDao.listComputer();
		for(Computer computer : listResults){
			printComputerDetails(computer);
		}
	}
	
	public void printComputerDetails(Computer computer) {
		System.out.print(computer.getId() + ", ");
		System.out.print(computer.getName() + ", ");
		System.out.print(computer.getIntroduced() + ", ");
		System.out.print(computer.getDiscontinued() + ", ");
		System.out.println(computer.getCompanyId());
	}
	
	public void showComputerDetails(String name) {
		List<Computer> listResults = computerDao.showComputerDetailsByName(name);
		for(Computer computer : listResults){
			printComputerDetails(computer);
		}
	}
	
	public void showComputerDetailsByID(int id) {
		List<Computer> listResults = computerDao.showComputerDetailsByID(id);
		for(Computer computer : listResults){
			printComputerDetails(computer);
		}
	}
	
	public void deleteComputer(int id) {
		computerDao.deleteComputer(id);
	}
	
	public void insertComputer(String name, String introduced, String discontinued, String companyID) {
		MapperComputer mapperComputer = MapperComputer.getInstance();
		Computer computer = mapperComputer.mapperComputer(name, introduced,discontinued,companyID);
		computerDao.createComputer(computer);
	}
	
	public void updateComputer(String id, String name, String introduced, String discontinued, String companyID) {
		MapperComputer mapperComputer = MapperComputer.getInstance();
		Computer computer = mapperComputer.mapperComputer(name,introduced,discontinued,companyID);
		computerDao.updateComputer(computer, Integer.parseInt(id));
	}
}
