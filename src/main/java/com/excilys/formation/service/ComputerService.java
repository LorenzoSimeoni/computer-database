package com.excilys.formation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.dao.ComputerDAO;
import com.excilys.formation.dao.OrderByComputer;
import com.excilys.formation.dao.OrderByMode;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Page;

@Service
public class ComputerService {
	
	@Autowired
	private ComputerDAO computerDao;
	
	private ComputerService(){}
	
	private static ComputerService computerServices = new ComputerService();
	
	public static ComputerService getInstance() {
		return computerServices;
	}
	
	public List<Computer> showComputer() {
		return computerDao.getList();
	}
	
	public List<Computer> showComputerPage(Page page) {
		return computerDao.getListPage(page);
	}

	public List<Computer> showComputerDetails(String name) {
		return computerDao.getDetailsByName(name);
	}

	public List<Computer> getComputerLike(String name, Page page) {
		return computerDao.getComputerLike(name,page);
	}
	
	public Optional<Computer> showComputerDetailsByID(long id) {
		return computerDao.getDetailsByID(id);
	}
	
	public List<Computer> showComputerDetailsByCompanyID(long id) {
		return computerDao.getDetailsByCompanyID(id);
	}
	
	public List<Computer> getListOrderBy(OrderByComputer column, OrderByMode mode, Page page) {
		return computerDao.getListOrderBy(column, mode, page);
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
	
	public int countComputer() {
		return computerDao.countComputer();
	}
	
	public int countComputerLike(String name) {
		return computerDao.countComputerLike(name);
	}
}
