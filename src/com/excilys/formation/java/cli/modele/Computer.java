/**
 * 
 */
package com.excilys.formation.java.cli.modele;

import java.time.LocalDateTime;

/**
 * @author excilys
 *
 */
public class Computer {
	private int id;
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private int companyId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDateTime getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDateTime introduced) {
		this.introduced = introduced;
	}
	public LocalDateTime getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDateTime discontinued) {
		this.discontinued = discontinued;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int company_id) {
		this.companyId = company_id;
	}

}
