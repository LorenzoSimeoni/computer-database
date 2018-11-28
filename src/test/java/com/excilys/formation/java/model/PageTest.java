package com.excilys.formation.java.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PageTest {
	
	private final int LIMIT = 10;
	private final int OFFSET = 10;
	private final int PAGENUMBER = 2;
	@Test
	public void testIncrementLimit() {
		Page page = new Page();
		page.setLimit(LIMIT);
		page.setOffset(OFFSET);
		page.setPageNumber(PAGENUMBER);
		
		page.incrementLimit();
		assertEquals(LIMIT + OFFSET, page.getLimit());
		assertEquals(PAGENUMBER + 1, page.getPageNumber());
	}

	@Test
	public void testDecreaseLimit() {
		Page page = new Page();
		page.setLimit(LIMIT);
		page.setOffset(OFFSET);
		page.setPageNumber(PAGENUMBER);
		
		page.decreaseLimit();
		assertEquals(LIMIT - OFFSET, page.getLimit());
		assertEquals(PAGENUMBER-1, page.getPageNumber());
	}

	@Test
	public void testChangePage() {
		Page page = new Page();
		page.setLimit(LIMIT);
		page.setOffset(OFFSET);
		page.setPageNumber(PAGENUMBER);
		
		page.changePage(PAGENUMBER + 5);
		assertEquals(PAGENUMBER + 5, page.getPageNumber());
		assertEquals(OFFSET*(PAGENUMBER + 5), page.getLimit());
	}

}
