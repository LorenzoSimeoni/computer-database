package com.excilys.formation.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NameException extends Exception {
	private final static Logger LOGGER = LogManager.getLogger(NameException.class.getName());

	private static final long serialVersionUID = 1L;

	public NameException() {
		LOGGER.info("You gived a computer with no name, can't achieve the creation or update");
	}
}