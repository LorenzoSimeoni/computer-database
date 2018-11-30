package com.excilys.formation.dto;

import java.time.LocalDateTime;

public class ComputerDTO {
	private long id;
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private String companyName;

	public ComputerDTO(ComputerDTOBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.companyName = builder.companyName;
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

	public static class ComputerDTOBuilder {
		private long id;
		private String name;
		private LocalDateTime introduced;
		private LocalDateTime discontinued;
		private String companyName;

		public ComputerDTOBuilder() {
		}

		public ComputerDTOBuilder(String name) {
			this.name = name;
		}

		public ComputerDTOBuilder setID(long id) {
			this.id = id;
			return this;
		}

		public ComputerDTOBuilder setIntroduced(LocalDateTime introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerDTOBuilder setDiscontinued(LocalDateTime discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public ComputerDTOBuilder setCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}

		public ComputerDTO build() {
			return new ComputerDTO(this);
		}
	}


}
