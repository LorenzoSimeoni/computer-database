package com.excilys.formation.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.exception.WebExceptions;

@ControllerAdvice
public class ErrorController {
	private final static Logger LOGGER = LogManager.getLogger(ErrorController.class.getName());
       
    @ExceptionHandler(value = { WebExceptions.class })
    protected ModelAndView handleNbElement(WebExceptions ex) {
    	LOGGER.error(ex.getMessage(),ex);
        return new ModelAndView(ex.getView());
    }
}