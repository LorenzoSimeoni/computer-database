package com.excilys.formation.controller;

public enum ViewName {
	DASHBOARD("dashboard"), 
	EDITCOMPUTER("editComputer"), 
	ADDCOMPUTER("addComputer");

	private String property;

	ViewName(String property) {
		this.property = property;
	}

	@Override
	public String toString() {
		return property;
	}
}
