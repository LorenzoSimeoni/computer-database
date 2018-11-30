package com.excilys.formation.webui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.cli.ComputerCLI;
import com.excilys.formation.mapper.MapperComputer;
import com.excilys.formation.model.Computer;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.validator.Validator;

/**
 * Servlet implementation class UpdateComputer
 */
@WebServlet("/updateComputer")
public class UpdateComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService = ComputerService.getInstance();
	private MapperComputer mapperComputer = MapperComputer.getInstance();
	private final static Logger LOGGER = LogManager.getLogger(ComputerCLI.class.getName());

    public UpdateComputer() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		this.getServletContext().getRequestDispatcher("/views/editComputer.jsp").forward(request, response);
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
				System.out.println(computer.toString());
				//computerService.updateComputer(computer);							
			} else {
				LOGGER.info("COMPUTER NOT CREATED");
			}
		}
		doGet(request, response);
	}

}
