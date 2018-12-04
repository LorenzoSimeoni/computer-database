package com.excilys.formation.webui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.dto.ComputerDTO;
import com.excilys.formation.dao.OrderByComputer;
import com.excilys.formation.dao.OrderByMode;
import com.excilys.formation.mapper.MapperComputerDTO;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Page;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;

/**
 * Servlet implementation class ShowComputers
 */
@WebServlet("/")
public class ShowComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService = ComputerService.getInstance();
	private CompanyService companyService = CompanyService.getInstance();
	private MapperComputerDTO mapperComputerDTO = MapperComputerDTO.getInstance();
	private int maxListPage = 5;
	private int minListPage = 1;

	public ShowComputers() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String order = request.getParameter("order");
		String mode = request.getParameter("mode");
		String nbElement = request.getParameter("nbElement");
		String numPage = request.getParameter("page");
		String name = request.getParameter("search");
		Page page = new Page();
		List<Computer> listComputer = new ArrayList<>();
		List<ComputerDTO> listComputerDTO = new ArrayList<>();
		int offset;
		int countComputer = 0;
		if (nbElement == null) {
			offset = 10;
			nbElement = "10";
		} else {
			offset = Integer.parseInt(nbElement);
		}
		page.setOffset(offset);
		if (numPage == null) {
			page.changePage(1);
		} else {
			page.changePage(Integer.parseInt(numPage));
		}
		if (name != null) {
			countComputer = computerService.countComputerLike(name);
			listComputer = computerService.getComputerLike(name, page);
		} else {
			OrderByComputer ordercolumn = chooseOrder(order);
			OrderByMode orderByMode = chooseMode(mode);
			countComputer = computerService.countComputer();
			listComputer = computerService.getListOrderBy(ordercolumn, orderByMode,page);
		}
		Stream<Computer> sp = listComputer.stream();
		sp.forEach(i -> {
			Optional<Company> companyName = companyService.showDetailsById(i.getCompany().getId());
			if (companyName.isPresent())
				listComputerDTO.add(mapperComputerDTO.mapper(i, companyName.get().getName()));
			else
				listComputerDTO.add(mapperComputerDTO.mapper(i, ""));
		});

		if (numPage != null) {
			int numberPage = Integer.parseInt(numPage);
			if (numberPage == maxListPage && listComputerDTO.size() == offset) {
				maxListPage++;
				minListPage++;
			} else if (numberPage == minListPage) {
				if (minListPage != 1) {
					maxListPage--;
					minListPage--;
				}
			}
		} else {
			numPage="1";
			maxListPage = 5;
			minListPage = 1;
		}
		request.setAttribute("nbElement", nbElement);
		request.setAttribute("numPage", numPage);
		request.setAttribute("search", name);
		request.setAttribute("order", order);
		request.setAttribute("mode", mode);
		request.setAttribute("maxListPage", maxListPage);
		request.setAttribute("minListPage", minListPage);
		request.setAttribute("listComputer", listComputerDTO);
		request.setAttribute("nbElementShown", listComputerDTO.size());
		request.setAttribute("sizeComputerFound", countComputer);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> deleted = Arrays.asList(request.getParameterValues("cb"));
		Stream<String> sp = deleted.stream();
		sp.forEach(id -> computerService.deleteComputer(Long.parseLong(id)));
		doGet(request, response);
	}
	
	public OrderByComputer chooseOrder(String order) {
	    if (order == null) {
	        return OrderByComputer.ID;
	      }
	    
	      switch (order) {
	      case "name" :
	        return OrderByComputer.NAME;
	      case "introduced" :
	        return OrderByComputer.INTRODUCED;
	      case "discontinued" :
	        return OrderByComputer.DISCONTINUED;
	      case "company" :
	        return OrderByComputer.COMPANY;
	      default:
	        return OrderByComputer.ID;
	  }
	}
	public OrderByMode chooseMode(String mode) {
	    if (mode == null) {
	        return OrderByMode.ASCENDING;
	      }
	    
	      switch (mode) {
	      case "asc" :
	        return OrderByMode.ASCENDING;
	      case "desc" :
	        return OrderByMode.DESCENDING;
	      default:
	        return OrderByMode.ASCENDING;
	  }
	}
}
