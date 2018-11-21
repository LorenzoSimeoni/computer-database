/**
 * 
 */
package com.excilys.formation.java.model;

import java.time.LocalDateTime;

/**
 * @author excilys
 *
 */
public class Computer {
	private long id;
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private Company company;
	
	public Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
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

	public Company getCompany() {
		return company;
	}
	
	@Override
	public String toString() {
		if(company.getId() == 0) {
			return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
					+ ", companyId=" + "null" + "]";
		} else {			
			return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
					+ ", companyId=" + company.getId() + "]";
		}
	}
	
	public static class ComputerBuilder {
		private long id;
		private String name;
		private LocalDateTime introduced;
		private LocalDateTime discontinued;
		private Company company;
		
		public ComputerBuilder(String name) {
			this.name = name;
		}
		
		public ComputerBuilder setID(long id) {
			this.id = id;
			return this; 
		}
		
		public ComputerBuilder setIntroduced(LocalDateTime introduced) {
			this.introduced = introduced;
			return this; 
		}
		
		public ComputerBuilder setDiscontinued(LocalDateTime discontinued) {
			this.discontinued = discontinued;
			return this; 
		}
		
		public ComputerBuilder setCompanyId(Company company) {
			this.company = company;
			return this; 
		}
		
		public Computer build() {
			return new Computer(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
