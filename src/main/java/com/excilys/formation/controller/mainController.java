package com.excilys.formation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.dao.OrderByComputer;
import com.excilys.formation.dao.OrderByMode;
import com.excilys.formation.dto.ComputerDTO;
import com.excilys.formation.exception.NotPermittedComputerException;
import com.excilys.formation.mapper.MapperComputer;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Page;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.validator.Validator;

@Controller
public class mainController {
	
	private final static Logger LOGGER = LogManager.getLogger(mainController.class.getName());

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private MapperComputer mapperComputer;
	@Autowired
	private Validator validator;

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
    public ModelAndView getUpdateComputer(@RequestParam(defaultValue = "") long id) {
		Optional<Computer> computer = computerService.showComputerDetailsByID(id);
		List<Company> listCompany = companyService.show();
		
        ModelAndView mv = new ModelAndView();
        mv.setViewName("editComputer");
        mv.getModel().put("id", id);
        mv.getModel().put("listCompany", listCompany);
        mv.getModel().put("computerName", computer.get().getName());
        mv.getModel().put("introduced", computer.get().getIntroduced());
        mv.getModel().put("discontinued", computer.get().getDiscontinued());
        mv.getModel().put("companyId", computer.get().getCompany().getId());
        return mv;
    }
	
	@PostMapping(value="/updateComputer")
	public ModelAndView postUpdateComputer(@RequestParam(defaultValue = "") String computerName,
			@RequestParam(defaultValue = "") String introduced,
			@RequestParam(defaultValue = "") String discontinued,
			@RequestParam(defaultValue = "") String companyId,
			@RequestParam(defaultValue = "") long id) {
		if (introduced.equals("")) {
			introduced = null;
		} else {
			introduced = introduced + "T00:00:00";
		}
		if (discontinued.equals("")) {
			discontinued = null;
		} else {
			discontinued = discontinued + "T00:00:00";
		}
		if (companyId.equals("")) {
			companyId = null;
		}
		Computer computer = mapperComputer.mapper(id, computerName, introduced, discontinued, companyId);
		try {
			validator.checkComputer(computer);
			computerService.updateComputer(computer);
		} catch (NotPermittedComputerException e) {
			LOGGER.info(" COMPUTER NOT UPDATED "+e.getErrorMsg(),e);
		}
        ModelAndView mv = new ModelAndView();
        return mv;
	}

	@GetMapping(value = "/addComputer")
    public ModelAndView getAddComputer() {
		List<Company> listCompany = companyService.show();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("addComputer");
        mv.getModel().put("listCompany", listCompany);
        return mv;
    }
	
	@PostMapping(value = "/addComputer")
    public ModelAndView postAddComputer(@RequestParam(defaultValue = "") String computerName,
    		@RequestParam(defaultValue = "") String introduced,
    		@RequestParam(defaultValue = "") String discontinued,
    		@RequestParam(defaultValue = "") String companyId) {
		
		if(!computerName.equals("")) {
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
			Computer computer = mapperComputer.mapper(computerName, introduced, discontinued,companyId);
			try {
				validator.checkComputer(computer);
				computerService.insertComputer(computer);
			} catch (NotPermittedComputerException e) {
				LOGGER.info(" COMPUTER NOT CREATED "+e.getErrorMsg(),e);
			}											
		}
        ModelAndView mv = new ModelAndView();
        mv.setViewName("addComputer");
        return mv;
    }
	
}
