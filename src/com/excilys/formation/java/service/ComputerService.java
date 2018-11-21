package com.excilys.formation.java.service;

import java.util.List;

import com.excilys.formation.java.dao.ComputerDAO;
import com.excilys.formation.java.model.Computer;

public class ComputerService {
	
	private ComputerDAO computerDao = ComputerDAO.getInstance();
	
	private ComputerService(){}
	
	private static ComputerService computerServices = new ComputerService();
	
	public static ComputerService getInstance() {
		return computerServices;
	}
	
	public List<Computer> showComputer() {
		return computerDao.getList();
	}
	
	public List<Computer> showComputerPage(int limit, int offset) {
		return computerDao.getListPage(limit, offset);
	}

	public List<Computer> showComputerDetails(String name) {
		return computerDao.getDetailsByName(name);
	}
	
	public Computer showComputerDetailsByID(long id) {
		return computerDao.getDetailsByID(id);
	}

	public int deleteComputer(long id) {
		return computerDao.delete(id);
	}
	
	public void insertComputer(Computer computer) {
		computerDao.create(computer);
	}
	
	public void updateComputer(Computer computer) {
		computerDao.update(computer);
	}
}
