package com.excilys.formation.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
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