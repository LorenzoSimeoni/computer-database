package com.excilys.formation.rawmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class MapperRawComputer implements RowMapper<Computer>{
	@Override
	public Computer mapRow(ResultSet results, int rowNum) throws SQLException {
		Company company = new Company.CompanyBuilder(results.getLong(5)).setName(results.getString(6)).build();

		String introduced = results.getString(3);
		LocalDateTime introducedDateAndTime = null;
		if (introduced != null) {
			introduced = introduced.replace(' ', 'T');
			introducedDateAndTime = LocalDateTime.parse(introduced);
		}
		LocalDateTime discontinuedDateAndTime = null;
		String discontinued = results.getString(4);
		if (discontinued != null) {
			discontinued = discontinued.replace(' ', 'T');
			discontinuedDateAndTime = LocalDateTime.parse(discontinued);
		}

		Computer computer = new Computer.ComputerBuilder(results.getString(2)).setID(results.getInt(1))
				.setIntroduced(introducedDateAndTime).setDiscontinued(discontinuedDateAndTime).setCompanyId(company)
				.build();

		return computer;
	}
}
