package com.excilys.formation.java.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.formation.java.mapper.MapperCompany;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;
import com.mysql.jdbc.Connection;

import junit.framework.Assert;

public class CompanyDAOTest {
	@Mock
	private ResultSet results;
	@Mock
	private PreparedStatement stmt;
	@Mock
	private Connection connection;
	@Mock
	private ConnectionDatabase connectionMock;
	@Mock
	private MapperCompany mapperCompany;
	@Mock
	private List<Company> list;
	@Mock
	private Page page;

	@InjectMocks
	private CompanyDAO companyDAO = CompanyDAO.getInstance();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.when(connectionMock.connect()).thenReturn(connection);
		Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(stmt);
		Mockito.when(stmt.executeQuery()).thenReturn(results);
	}

	@Test
	public void testGetList() throws SQLException {
		int id = 10;
		Company company = new Company.CompanyBuilder(id).setName("company" + id).build();
		List<Company> companies = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			companies.add(company);
		}
		Mockito.when(mapperCompany.mapper(results)).thenReturn(company);
		Mockito.when(results.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);

		List<Company> result = companyDAO.getList();

		Assert.assertEquals("The returned list is different", companies, result);
	}

	@Test
	public void testGetDetailsById() throws SQLException {
		int id = 1;
		Company company = new Company.CompanyBuilder(id).setName("company" + id).build();
		Mockito.when(results.next()).thenReturn(true).thenReturn(false);
		Mockito.when(mapperCompany.mapper(results)).thenReturn(company);

		Company result = companyDAO.getDetailsById(1);

		Assert.assertEquals("The returned company is different", company, result);
	}

	@Test
	public void testGetListPage() throws SQLException {
		int id = 10;
		Company company = new Company.CompanyBuilder(id).setName("company" + id).build();
		List<Company> companies = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			companies.add(company);
		}
		Mockito.when(page.getLimit()).thenReturn(10);
		Mockito.when(page.getOffset()).thenReturn(10);
		Mockito.when(results.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(mapperCompany.mapper(results)).thenReturn(company);

		List<Company> result = companyDAO.getListPage(page);

		Assert.assertEquals("The returned list is different", companies, result);
	}
}
