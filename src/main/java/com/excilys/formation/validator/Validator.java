package com.excilys.formation.validator;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.service.CompanyService;

public class Validator {
	private final static Logger LOGGER = LogManager.getLogger(Validator.class.getName());

	public static void checkComputer(Computer computer) throws DateException,NameException,CompanyIDException {
		if (computer.getName() == null) {
			throw new NameException();
		}
		if (!companyIdExist(computer.getCompany().getId())) {
			throw new CompanyIDException();
		}
		if (computer.getDiscontinued() != null && computer.getIntroduced() == null) {
			LOGGER.info(
					"You can't have Discontinued date without introduced date, can't achieve the creation or update of this computer");
			throw new DateException();
		}
		if (computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			if (!computer.getIntroduced().isBefore(computer.getIntroduced())) {
				LOGGER.info(
						"A discontinued date can't be before an Introduced date, can't achieve the creation or update of this computer");
				throw new DateException();
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
