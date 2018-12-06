package com.excilys.formation.configSpring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.excilys.formation.mapper",
		"com.excilys.formation.dao",
		"com.excilys.formation.cli",
		"com.excilys.formation.service",
		"com.excilys.formation.controller"})
public class Config {
	
}
