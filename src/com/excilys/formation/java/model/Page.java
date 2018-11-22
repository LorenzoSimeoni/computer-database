package com.excilys.formation.java.model;

public class Page {
	private int limit;
	private int offset;
	private int pageNumber;
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void incrementLimit() {
		limit = limit + offset;
		pageNumber++;
	}
	public void decreaseLimit() {
		if(this.limit - offset >= 0) {
			limit = limit - offset;
		}
		else {
			limit = 0;
		}
		if(pageNumber>0) {
			pageNumber--;
		}
		else {
			pageNumber = 0;
		}
	}
	public void changePage(int newPage) {
		int difference = newPage - pageNumber;
		if(difference > 0) {
			for(int k = 0; k<difference; k++) {
				incrementLimit();
			}			
		} else {
			difference = -difference;
			for(int k = 0; k<difference; k++) {
				decreaseLimit();
			}		
		}
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}
