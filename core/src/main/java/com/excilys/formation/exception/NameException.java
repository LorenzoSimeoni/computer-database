package com.excilys.formation.exception;

public class NameException extends NotPermittedComputerException {
	private static final long serialVersionUID = 1L;
	private String ErrorMsg;
	
	public String getErrorMsg() {
		return ErrorMsg;
	}
	public NameException() {
		ErrorMsg = "You gived a computer with no name, can't achieve the creation or update";
	}
}