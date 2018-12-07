package com.excilys.formation.validator;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.exception.CompanyIDException;
import com.excilys.formation.exception.DateDiscontinuedIsBeforIntroducedException;
import com.excilys.formation.exception.DateDiscontinuedWithoutIntroduced;
import com.excilys.formation.exception.NameException;
import com.excilys.formation.exception.NotPermittedComputerException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.service.CompanyService;

public class Validator {
	private final static Logger LOGGER = LogManager.getLogger(Validator.class.getName());

	public static void checkComputer(Computer computer) throws NotPermittedComputerException {
		if (computer.getName() == null) {
			throw new NameException();
		}
		if (!companyIdExist(computer.getCompany().getId())) {
			throw new CompanyIDException();
		}
		if (computer.getDiscontinued() != null && computer.getIntroduced() == null) {
			throw new DateDiscontinuedWithoutIntroduced();
		}
		if (computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			if (!computer.getDiscontinued().isBefore(computer.getIntroduced())) {
				throw new DateDiscontinuedIsBeforIntroducedException();
			}
		}
	}
	
	public static boolean companyIdExist(long id) {
		if(id == 0) {
			return true;
		}
		CompanyService companyServices = CompanyService.getInstance();
		Optional<Company> company = companyServices.showDetailsById(id);
		if(!company.isPresent()) {
			LOGGER.info("You gived a wrong CompanyID, it doesn't exist !");
			return false;			
		}
		return true;
	}
}
