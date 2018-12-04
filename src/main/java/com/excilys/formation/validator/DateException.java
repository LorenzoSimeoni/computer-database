package com.excilys.formation.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateException extends Exception {
	private final static Logger LOGGER = LogManager.getLogger(DateException.class.getName());

	private static final long serialVersionUID = 1L;

	public DateException() {
		LOGGER.error("DATE EXCEPTION!");
	}
}
