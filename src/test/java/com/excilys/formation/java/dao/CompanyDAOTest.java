package com.excilys.formation.java.dao;

import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;

import com.excilys.formation.java.mapper.MapperCompany;
import com.excilys.formation.java.model.Company;

public class CompanyDAOTest {

	@Mock
	ResultSet results;
	@Mock
	PreparedStatement stmt;
	@Mock
	ConnectionDatabase connectionMock;
	@Mock
	MapperCompany mapper = MapperCompany.getInstance();

	
	List<Company> list = new ArrayList<Company>();

	@Test
	public void testGetList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDetailsById() {
		fail("Not yet implemented");
//		int id = 1;
//		Company company = CompanyDAO.getInstance().getDetailsById(1);
//		assertEquals(id, company.getId());
	}

	@Test
	public void testGetListPage() {
		fail("Not yet implemented");
	}

}
