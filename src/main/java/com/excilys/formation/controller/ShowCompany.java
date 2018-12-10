package com.excilys.formation.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.model.Company;
import com.excilys.formation.service.CompanyService;

/**
 * Servlet implementation class Features
 */
@WebServlet("/showCompany")
public class ShowCompany extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CompanyService companyService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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