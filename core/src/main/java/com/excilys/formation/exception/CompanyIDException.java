package com.excilys.formation.exception;


public class CompanyIDException extends NotPermittedComputerException {

	private static final long serialVersionUID = 1L;
	String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	
	public CompanyIDException() {
		ErrorMsg="You gived a wrong Company ID, can't achieve the creation or update of computer";
	}
}