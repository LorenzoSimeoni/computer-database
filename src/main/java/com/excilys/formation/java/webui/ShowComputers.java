package com.excilys.formation.java.webui;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.service.ComputerService;

/**
 * Servlet implementation class ShowComputers
 */
@WebServlet("/showComputer")
public class ShowComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService = ComputerService.getInstance();
	
    public ShowComputers() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int size = 10;
		Page page = new Page();
		page.setOffset(size);
		List<Computer> listComputer = computerService.showComputerPage(page);
		request.setAttribute("listComputer", listComputer);
		this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
