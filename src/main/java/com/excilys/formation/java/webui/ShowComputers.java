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
	private int maxListPage = 5;
	private int minListPage = 1;
	
    public ShowComputers() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nbElement = request.getParameter("nbElement");
		String numPage = request.getParameter("page");
		String name = request.getParameter("search");
		List<Computer> listComputer;
		if(name != null && !"".equals(name)) {
			listComputer = computerService.showComputerDetails(name);
		}
		else {
			Page page = new Page();
			int size;
			
			if(nbElement == null) {
				size = 10;
			} else {
				size = Integer.parseInt(nbElement);
			}
			page.setOffset(size);
			if(numPage == null) {
				page.changePage(1);
			} else {
				page.changePage(Integer.parseInt(numPage));
			}
			listComputer = computerService.showComputerPage(page);			
			if(numPage != null) {
				int numberPage = Integer.parseInt(numPage);			
				if( numberPage == maxListPage && listComputer.size() == size) {
					maxListPage++;
					minListPage++;
				} else if(numberPage == minListPage) {
					if(minListPage != 1) {
						maxListPage--;
						minListPage--;				
					}
				}
			} else {
				maxListPage = 5;
				minListPage = 1;
			}
			request.setAttribute("maxListPage", maxListPage);
			request.setAttribute("minListPage", minListPage);
		}
		request.setAttribute("listComputer", listComputer);
		request.setAttribute("sizeComputerFound", listComputer.size());
		this.getServletContext().getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
