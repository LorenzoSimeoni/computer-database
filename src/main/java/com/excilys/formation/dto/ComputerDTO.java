package com.excilys.formation.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.excilys.formation.model.Computer;

public class ComputerDTO {
	private long id;
	@NotBlank
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private String companyName;
	
	public ComputerDTO(Computer computer) {
		this.id = computer.getId();
		this.name = computer.getName();
		this.introduced = computer.getIntroduced();
		this.discontinued = computer.getDiscontinued();
		this.companyName = computer.getCompany().getName();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getIntroduced() {
		return introduced;
	}

	public LocalDateTime getDiscontinued() {
		return discontinued;
	}
	public String getCompanyName() {
		return companyName;
	}


	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", companyId=" + companyName + "]";
	}
}
