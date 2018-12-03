package com.excilys.formation.mapper;

import com.excilys.formation.dto.ComputerDTO;
import com.excilys.formation.model.Computer;

public class MapperComputerDTO {

	private MapperComputerDTO(){}
	
	private static MapperComputerDTO mapperComputerDTO = new MapperComputerDTO();
	
	public static MapperComputerDTO getInstance() {
		return mapperComputerDTO;
	}
	
	public ComputerDTO mapper(Computer computer, String companyName) {
		ComputerDTO computerDTO = new ComputerDTO.ComputerDTOBuilder(computer.getName())
				.setID(computer.getId())
				.setIntroduced(computer.getIntroduced())
				.setDiscontinued(computer.getDiscontinued())
				.setCompanyName(companyName)
				.build();
		return computerDTO;
	}
	
}
