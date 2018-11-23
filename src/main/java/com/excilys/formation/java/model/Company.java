/**
 * 
 */
package com.excilys.formation.java.model;

/**
 * @author excilys
 *
 */
public class Company {
	private long id;
	private String name;
	
	public Company(CompanyBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
	
	public static class CompanyBuilder {
		private long id;
		private String name;
		
		public CompanyBuilder() {}
		
		public CompanyBuilder(long id) {
			this.id = id;
		}
		
		public CompanyBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		public Company build() {
			return new Company(this);
		}
	}
}


