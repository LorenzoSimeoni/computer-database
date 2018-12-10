package com.excilys.formation.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.cli.ComputerCLI;
import com.excilys.formation.exception.NotPermittedComputerException;
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
	private final static Logger LOGGER = LogManager.getLogger(ComputerCLI.class.getName());

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private MapperComputer mapperComputer;
	@Autowired
	private Validator validator;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public UpdateComputer() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		String id = request.getParameter("id");
		if (introduced.equals("")) {
			introduced = null;
		} else {
			introduced = introduced + "T00:00:00";
		}
		if (discontinued.equals("")) {
			discontinued = null;
		} else {
			discontinued = discontinued + "T00:00:00";
		}
		if (companyId.equals("")) {
			companyId = null;
		}
		Computer computer = mapperComputer.mapper(Long.parseLong(id), name, introduced, discontinued, companyId);
		try {
			validator.checkComputer(computer);
			computerService.updateComputer(computer);
		} catch (NotPermittedComputerException e) {
			LOGGER.info(" COMPUTER NOT UPDATED "+e.getErrorMsg(),e);
		}
		doGet(request, response);
	}

}
