package com.excilys.formation.java.webui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.java.cli.ComputerCLI;
import com.excilys.formation.java.mapper.MapperComputer;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.ComputerService;
import com.excilys.formation.java.validator.Validator;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService = ComputerService.getInstance();
	private MapperComputer mapperComputer = MapperComputer.getInstance();
	private final static Logger LOGGER = LogManager.getLogger(ComputerCLI.class.getName());


    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO
		this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("computerName");     
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		if(!name.equals("")) {
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
			Computer computer = mapperComputer.mapper(name, introduced, discontinued,companyId);
			if(Validator.checkComputer(computer)) {
				computerService.insertComputer(computer);							
			} else {
				LOGGER.info("COMPUTER NOT CREATED");
			}
		}
        this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
	}

}
