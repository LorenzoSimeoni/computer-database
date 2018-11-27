package com.excilys.formation.java.webui;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.ComputerService;

/**
 * Servlet implementation class ShowComputers
 */
public class ShowComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService = ComputerService.getInstance();
	
    public ShowComputers() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Computer> listComputer = computerService.showComputer();
		request.setAttribute("listComputer", listComputer);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/showComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
