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
		if (nameIsNull(computer)) {
			throw new NameException();
		}
		if (!companyIdExist(computer.getCompany().getId())) {
			throw new CompanyIDException();
		}
		if (discontinuedNotNullWhileIntroducedIs(computer)) {
			throw new DateDiscontinuedWithoutIntroduced();
		}
		if(discontinuedNotAfterIntroduced(computer)) {
			throw new DateDiscontinuedIsBeforIntroducedException();
		}
	}
	
	private static boolean nameIsNull(Computer computer) {
		if (computer.getName() == null) {
			return true;			
		}
		return false;
	}
	
	private static boolean discontinuedNotNullWhileIntroducedIs(Computer computer) {
		if (computer.getDiscontinued() != null && computer.getIntroduced() == null) {
			return true;
		}
		return false;
	}
	
	private static boolean discontinuedNotAfterIntroduced(Computer computer) {
		if (computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			if (!computer.getDiscontinued().isBefore(computer.getIntroduced())) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean companyIdExist(long id) {
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
