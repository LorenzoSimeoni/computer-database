package com.excilys.formation.model;

public class Page {
	private int limit = 0;
	private int offset = 10;
	private int pageNumber = 1;
	private int maxNumberPage = 1;
	private int minNumberPage = 9;
	private final int firstMiddleNumerotation = 5;
	
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
		changeMaxAndMin();
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
	public int getMaxNumberPage() {
		return maxNumberPage;
	}
	public void setMaxNumberPage(int maxNumberPage) {
		this.maxNumberPage = maxNumberPage;
	}
	public int getMinNumberPage() {
		return minNumberPage;
	}
	public void setMinNumberPage(int minNumberPage) {
		this.minNumberPage = minNumberPage;
	}
	public void changeMaxAndMin() {
		if(pageNumber>(maxNumberPage+minNumberPage/2)) {
			maxNumberPage++;
			minNumberPage++;
		} else if(pageNumber<(maxNumberPage+minNumberPage/2)&&pageNumber>=firstMiddleNumerotation) {
			maxNumberPage--;
			minNumberPage--;				
			
		}
	}
	
	@Override
	public String toString() {
		return "Page [limit=" + limit + ", offset=" + offset + ", pageNumber=" + pageNumber + "]";
	}
}
