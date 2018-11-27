package com.excilys.formation.java.webui;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.service.CompanyService;

/**
 * Servlet implementation class Features
 */
public class ShowCompany extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CompanyService companyService = CompanyService.getInstance();

    public ShowCompany() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> listCompany = companyService.show();
		request.setAttribute("listCompany", listCompany);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/showCompany.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}