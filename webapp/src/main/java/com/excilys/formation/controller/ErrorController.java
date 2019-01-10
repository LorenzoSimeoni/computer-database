package com.excilys.formation.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.exception.PageNumberException;
import com.excilys.formation.exception.IdException;

@ControllerAdvice
public class ErrorController {
	private final static Logger LOGGER = LogManager.getLogger(ErrorController.class.getName());
       
    @ExceptionHandler(value = { PageNumberException.class })
    protected ModelAndView handleNbElement(PageNumberException ex) {
    	LOGGER.error(ex.getMessage(),ex);
        return new ModelAndView(ex.getView());
    }
    
//    @ExceptionHandler(value = { IdException.class })
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    protected ModelAndView handleNbElement(IdException ex) {
//    	LOGGER.error(ex.getMessage(),ex);
//        return new ModelAndView(ex.getView());
//    }
}