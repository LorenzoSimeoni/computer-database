package com.excilys.formation.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompanyIDException extends Exception {
	private final static Logger LOGGER = LogManager.getLogger(CompanyIDException.class.getName());

	private static final long serialVersionUID = 1L;

	public CompanyIDException() {
		LOGGER.info("You gived a wrong Company ID, can't achieve the creation or update of computer");
	}
}