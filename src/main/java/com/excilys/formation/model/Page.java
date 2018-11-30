package com.excilys.formation.model;

public class Page {
	private int limit;
	private int offset;
	private int pageNumber = 1;
	
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
		if(limit - offset >= 0) {
			limit = limit - offset;
		}
		else {
			limit = 0;
		}
		if(pageNumber>1) {
			pageNumber--;
		}
		else {
			pageNumber = 1;
		}
	}
	public void changePage(int newPage) {
		if(newPage<1) {
			newPage=1;
		}
		limit = newPage*offset-offset;
		pageNumber = newPage;
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
