package com.excilys.formation.java.cli.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.java.cli.mapper.MapperCompany;
import com.excilys.formation.java.cli.modele.Company;

public class CompanyDAO {
	
	private PreparedStatement stmt;
	private ResultSet results;
	private static final String LISTCOMPANY = "SELECT * FROM company;";
	
	public List<Company> listCompany() {
		ConnectionCLI cli = new ConnectionCLI();
		List<Company> list = new ArrayList<Company>();
		try {
			stmt = cli.connect().prepareStatement(LISTCOMPANY);
			results = stmt.executeQuery();
			MapperCompany mapperCompany = new MapperCompany();
			while(results.next()) {
				Company company = mapperCompany.mapperCompany(results);
				list.add(company);
			}
		} catch (SQLException e) {
			System.out.println("Exception due à la requète LISTCOMPANY");
			e.printStackTrace();
		}
		finally {
			cli.disconnect();
			if (results != null) try { results.close(); } catch (SQLException ignore) {}
	        if (stmt != null) try { stmt.close(); } catch (SQLException ignore) {}
		}
		return list;
	}
}
