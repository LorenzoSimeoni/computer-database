package com.excilys.formation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.dao.OrderByComputer;
import com.excilys.formation.dao.OrderByMode;
import com.excilys.formation.dto.ComputerDTO;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Page;
import com.excilys.formation.service.ComputerService;

@Controller
public class mainController {
	
	@Autowired
	private ComputerService computerService;

	@GetMapping(value = "/")
    public ModelAndView getDashboardPage(@RequestParam(defaultValue = "") String order,
    		@RequestParam(defaultValue = "") String mode,
    		@RequestParam(defaultValue = "10") int nbElement,
    		@RequestParam(defaultValue = "1") int page,
    		@RequestParam(defaultValue = "") String search) {
		Page ourPage = new Page();
		List<Computer> listComputer = new ArrayList<>();
		List<ComputerDTO> listComputerDTO = new ArrayList<>();
		int countComputer = 0;
		ourPage.setOffset(nbElement);
		ourPage.changePage(page);
		OrderByComputer orderColumn = OrderByComputer.myValueOf(order);
		OrderByMode orderByMode = OrderByMode.myValueOf(mode);
		if (search.equals("")) {
			countComputer = computerService.countComputer();
			listComputer = computerService.getListOrderBy(orderColumn, orderByMode, ourPage);
		} else {
			countComputer = computerService.countComputerLike(search);
			listComputer = computerService.getComputerOrderByLike(orderColumn, orderByMode, search, ourPage);
		}
		listComputer.stream().forEach(i -> {
			listComputerDTO.add(new ComputerDTO(i));
		});
		ModelAndView mv = new ModelAndView();
        mv.setViewName("dashboard");
        mv.getModel().put("nbElement", nbElement);
        mv.getModel().put("numPage", page);
        mv.getModel().put("search", search);
        mv.getModel().put("order", order);
        mv.getModel().put("mode", mode);
        mv.getModel().put("maxListPage", 5);
        mv.getModel().put("minListPage", 1);
        mv.getModel().put("listComputer", listComputerDTO);
        mv.getModel().put("nbElementShown", listComputerDTO.size());
        mv.getModel().put("sizeComputerFound", countComputer);
        return mv;
    }
	
	@PostMapping(value = "/")
    public ModelAndView postDashboardPage(@RequestParam(defaultValue = "") String cb) {
		List<String> deleted = Arrays.asList(cb);
		Stream<String> sp = deleted.stream();
		sp.forEach(id -> computerService.deleteComputer(Long.parseLong(id)));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("dashboard");
		return mv;
	}

	

	@GetMapping(value = "/updateComputer")
    public ModelAndView getUpdateComputer() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("editComputer");
        mv.getModel().put("data", "Welcome home man");
 
        return mv;
    }

	@GetMapping(value = "/addComputer")
    public ModelAndView getAddComputer() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("addComputer");
        mv.getModel().put("data", "Welcome home man");
 
        return mv;
    }
}
