package com.excilys.formation.java.webui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.java.mapper.MapperComputer;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.ComputerService;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService = ComputerService.getInstance();
	private MapperComputer mapperComputer = MapperComputer.getInstance();


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
			}
			if(discontinued.equals("")) {
				discontinued = null;
			}
			if(companyId.equals("")) {
				companyId = null;
			}
			System.out.println(name);
			System.out.println(introduced);
			System.out.println(discontinued);
			System.out.println(companyId);
			Computer computer = mapperComputer.mapper(name, introduced, discontinued,companyId);
			System.out.println(computer.toString());
			computerService.insertComputer(computer);			
		}
		
        this.getServletContext().getRequestDispatcher("/views/addComputer.jsp").forward(request, response);
	}

}
