package com.excilys.formation.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.cli.ComputerCLI;
import com.excilys.formation.exception.CompanyIDException;
import com.excilys.formation.exception.DateException;
import com.excilys.formation.exception.NameException;
import com.excilys.formation.mapper.MapperComputer;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.validator.Validator;

/**
 * Servlet implementation class UpdateComputer
 */
@WebServlet("/updateComputer")
public class UpdateComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService = ComputerService.getInstance();
	private CompanyService companyService = CompanyService.getInstance();
	private MapperComputer mapperComputer = MapperComputer.getInstance();
	private final static Logger LOGGER = LogManager.getLogger(ComputerCLI.class.getName());

    public UpdateComputer() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		Optional<Computer> computer = computerService.showComputerDetailsByID(Long.parseLong(id));
		List<Company> listCompany = companyService.show();
		request.setAttribute("listCompany", listCompany);
		request.setAttribute("computerName", computer.get().getName());
		request.setAttribute("introduced", computer.get().getIntroduced());
		request.setAttribute("discontinued", computer.get().getDiscontinued());
		request.setAttribute("companyId", computer.get().getCompany().getId());
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("computerName");     
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		String id = request.getParameter("id");
		if(introduced.equals("")) {
			introduced = null;
		} else {
			introduced = introduced+"T00:00:00";
		}
		if(discontinued.equals("")) {
			discontinued = null;
		} else {
			discontinued = discontinued+"T00:00:00";
		}
		if(companyId.equals("")) {
			companyId = null;
		}
		Computer computer = mapperComputer.mapper(Long.parseLong(id), name, introduced, discontinued,companyId);
		try {
			Validator.checkComputer(computer);
			computerService.updateComputer(computer);							
		} catch (DateException|NameException|CompanyIDException e) {
			LOGGER.info("COMPUTER NOT CREATED");
		}	
		doGet(request, response);
	}

}
