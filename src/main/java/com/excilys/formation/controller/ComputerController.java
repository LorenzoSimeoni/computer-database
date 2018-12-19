package com.excilys.formation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.dao.OrderByComputer;
import com.excilys.formation.dao.OrderByMode;
import com.excilys.formation.dto.ComputerDTO;
import com.excilys.formation.exception.NbElementException;
import com.excilys.formation.exception.NotPermittedComputerException;
import com.excilys.formation.exception.PageNumberException;
import com.excilys.formation.mapper.MapperComputer;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Page;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;
import com.excilys.formation.validator.Validator;

@Controller
@RequestMapping(value="/Computer")
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
	
	

	@GetMapping(params="nbElement")
	public ModelAndView changeNbElementsPage(@RequestParam int nbElement) throws NbElementException {
		if(nbElement<=0) {
			throw new NbElementException();
		} 
		pagination.setOffset(nbElement);
		pagination.resetNumerotation();
		return constructPage();
	}
	@GetMapping(params="numPage")
	public ModelAndView changeNumPage(@RequestParam int numPage) throws PageNumberException {
		if(numPage > pagination.getMAX()) {
			throw new PageNumberException();
		}
		pagination.changePage(numPage);
		return constructPage();
	}
	@GetMapping(params="search")
	public ModelAndView search(@RequestParam String search) {
		this.search = search;
		pagination.resetNumerotation();
		return constructPage();
	}
	@GetMapping(params= {"order"})
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

	@GetMapping
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
		long countComputer = 0;
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
		pagination.setMAX((int)countComputer/pagination.getOffset()+1);
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
	
	@PostMapping
    public String postDashboardPage(@RequestParam(defaultValue = "") String cb) {
		List<String> deleted = Arrays.asList(cb);
		deleted.stream().forEach(id -> computerService.deleteComputer(Long.parseLong(id)));
		return ViewName.DASHBOARD.toString();
	}

	@GetMapping(value = "/update")
    public ModelAndView getUpdateComputer(@RequestParam(defaultValue = "0") long id) {
		Optional<Computer> computerOpt = computerService.showComputerDetailsByID(id);
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewName.EDITCOMPUTER.toString());
		if(computerOpt.isPresent()) {
			Computer computer = computerOpt.get();
			List<Company> listCompany = companyService.show();
			
			mv.addObject("computerDTO", new ComputerDTO(computer));
			mv.getModel().put("id", id);
			mv.getModel().put("listCompany", listCompany);
			return mv;
		}
		return mv;
    }
	
	@PostMapping(value="/update")
	public ModelAndView postEditComputer(@Valid @ModelAttribute("computerDTO")ComputerDTO computerDTO,
			BindingResult bindingResult) {
		if(!bindingResult.hasErrors()) {
			Computer computer = mapperComputer.mapper(computerDTO);
			try {
				validator.checkComputer(computer);
				computerService.updateComputer(computer);
			} catch (NotPermittedComputerException e) {
				LOGGER.info(" COMPUTER NOT UPDATED "+e.getErrorMsg(),e);
			}			
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName(ViewName.EDITCOMPUTER.toString());
		mv.getModel().put("id", computerDTO.getId());
		return mv; 
	}

	@GetMapping(value = "/add")
    public ModelAndView getAddComputer() {
		List<Company> listCompany = companyService.show();
        ModelAndView mv = new ModelAndView();
        mv.setViewName(ViewName.ADDCOMPUTER.toString());
        mv.addObject("computerDTO", new ComputerDTO());
        mv.getModel().put("listCompany", listCompany);
        return mv;
    }
	
	@PostMapping(value = "/add")
	public String postAddComputer(@Valid @ModelAttribute("computerDTO")ComputerDTO computerDTO,
			BindingResult bindingResult) {
		if(!bindingResult.hasErrors()) {
			Computer computer = mapperComputer.mapper(computerDTO);
			System.out.println(computer.toString());
			try {
				validator.checkComputer(computer);
				computerService.insertComputer(computer);
			} catch (NotPermittedComputerException e) {
				LOGGER.info(" COMPUTER NOT CREATED "+e.getErrorMsg(),e);
			}
		} 
		return ViewName.ADDCOMPUTER.toString();
	}
	
    @GetMapping(value = "errors")
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
         
        ModelAndView errorPage = new ModelAndView();
        int httpErrorCode = getErrorCode(httpRequest);
 
        switch (httpErrorCode) {
            case 403: {
                errorPage.setViewName(ViewName.FORBIDDEN.toString());
                break;
            }
            case 404: {
                errorPage.setViewName(ViewName.NOTFOUND.toString());
                break;
            }
            case 500: {
                errorPage.setViewName(ViewName.INTERNALSERVERERROR.toString());
                break;
            }
            default:
            	errorPage.setViewName(ViewName.NOTFOUND.toString());
            	break;
        }        
        return errorPage;
    }
     
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
          .getAttribute("javax.servlet.error.status_code");
    }
}
