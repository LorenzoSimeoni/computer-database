package com.excilys.formation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
public class ComputerController {
	
	private final static Logger LOGGER = LogManager.getLogger(ComputerController.class.getName());

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private MapperComputer mapperComputer;
	@Autowired
	private Validator validator;
	
	private String order = "";
	private String mode = "";
	private boolean columnComputerName = false;
	private boolean columnIntroduced = false;
	private boolean columnDiscontinued = false;
	private boolean columnCompanyName = false;
	private Page pagination = new Page();
	private String search = "";

	@GetMapping(value = "/", params="nbElement")
	public ModelAndView changeNbElementsPage(@RequestParam int nbElement) {
		pagination.setOffset(nbElement);
		pagination.resetNumerotation();
		return constructPage();
	}
	@GetMapping(value = "/", params="numPage")
	public ModelAndView changeNumPage(@RequestParam int numPage) {
		pagination.changePage(numPage);
		return constructPage();
	}
	@GetMapping(value = "/", params="search")
	public ModelAndView search(@RequestParam String search) {
		this.search = search;
		pagination.resetNumerotation();
		return constructPage();
	}
	@GetMapping(value = "/", params= {"order"})
	public ModelAndView orderByColumnComputeName(@RequestParam String order) {
		this.order = order;
		switch (this.order) {
		case "name":
			if(columnComputerName) {
				if(mode.equals("asc")) {
					mode = "desc";
				} else {
					mode = "asc";
				}
			} else {
				columnComputerName = true;
				columnIntroduced = false;
				columnDiscontinued = false;
				columnCompanyName = false;
				mode = "asc";
			}
			break;
		case "introduced":
			if(columnIntroduced) {
				if(mode.equals("asc")) {
					mode = "desc";
				} else {
					mode = "asc";
				}
			} else {
				columnIntroduced = true;
				columnComputerName = false;
				columnDiscontinued = false;
				columnCompanyName = false;
				mode = "asc";
			}
			break;
		case "discontinued":
			if(columnDiscontinued) {
				if(mode.equals("asc")) {
					mode = "desc";
				} else {
					mode = "asc";
				}
			} else {
				columnDiscontinued = true;
				columnComputerName = false;
				columnIntroduced = false;
				columnCompanyName = false;
				mode = "asc";
			}
			break;
		case "company":
			if(columnCompanyName) {
				if(mode.equals("asc")) {
					mode = "desc";
				} else {
					mode = "asc";
				}
			} else {
				columnCompanyName = true;
				columnComputerName = false;
				columnIntroduced = false;
				columnDiscontinued = false;
				mode = "asc";
			}
			break;
		}
		return constructPage();
	}

	
	
	@GetMapping(value = "/")
	public ModelAndView getDashboardPage() {
		resetAll();
		return constructPage();
	}
	
	private void resetAll() {
		order = "";
		mode = "";
		pagination.resetNumerotation();
		pagination.setOffset(10);
		pagination.setLimit(0);
		search = "";
	}
	
	private ModelAndView constructPage() {
		OrderByComputer orderColumn = OrderByComputer.myValueOf(order);
		OrderByMode orderByMode = OrderByMode.myValueOf(mode);
		List<Computer> listComputer = new ArrayList<>();
		List<ComputerDTO> listComputerDTO = new ArrayList<>();
		int countComputer = 0;
		if (search.equals("")) {
			countComputer = computerService.countComputer();
			listComputer = computerService.getListOrderBy(orderColumn, orderByMode, pagination);
		} else {
			countComputer = computerService.countComputerLike(search);
			listComputer = computerService.getComputerOrderByLike(orderColumn, orderByMode, search, pagination);
		}
		listComputer.stream().forEach(i -> {
			listComputerDTO.add(new ComputerDTO(i));
		});
		pagination.setMAX(countComputer/pagination.getOffset()+1);
		pagination.checkMax();
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewName.DASHBOARD.toString());
		mv.getModel().put("pagination",pagination);
        mv.getModel().put("search", search);
        mv.getModel().put("order", order);
        mv.getModel().put("mode", mode);
        mv.getModel().put("listComputer", listComputerDTO);
        mv.getModel().put("nbElementShown", listComputerDTO.size());
        mv.getModel().put("sizeComputerFound", countComputer);
		return mv;
	}
	
	@PostMapping(value = "/")
    public String postDashboardPage(@RequestParam(defaultValue = "") String cb) {
		List<String> deleted = Arrays.asList(cb);
		deleted.stream().forEach(id -> computerService.deleteComputer(Long.parseLong(id)));
		return ViewName.DASHBOARD.toString();
	}

	@GetMapping(value = "/updateComputer")
    public ModelAndView getUpdateComputer(@RequestParam(defaultValue = "0") long id) {
		Optional<Computer> computerOpt = computerService.showComputerDetailsByID(id);
		Computer computer = computerOpt.get();
		List<Company> listCompany = companyService.show();
		
        ModelAndView mv = new ModelAndView();
        mv.setViewName(ViewName.EDITCOMPUTER.toString());
        mv.getModel().put("id", id);
        mv.getModel().put("listCompany", listCompany);
        mv.getModel().put("computer", computer);
        return mv;
    }
	
	
	
	@PostMapping(value="/updateComputer")
	public String postUpdateComputer(@RequestParam(defaultValue = "") String computerName,
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
        return ViewName.EDITCOMPUTER.toString();
	}

	@GetMapping(value = "/addComputer")
    public ModelAndView getAddComputer() {
		List<Company> listCompany = companyService.show();
        ModelAndView mv = new ModelAndView();
        mv.setViewName(ViewName.ADDCOMPUTER.toString());
        mv.getModel().put("listCompany", listCompany);
        return mv;
    }
	
	@PostMapping(value = "/addComputer")
    public String postAddComputer(@RequestParam(defaultValue = "") String computerName,
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
        return ViewName.ADDCOMPUTER.toString();
    }
	@GetMapping(value = "/*")
	public String errorPage() {
		return "404";
	}
}
