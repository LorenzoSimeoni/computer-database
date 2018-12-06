package com.excilys.formation.configSpring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.excilys.formation.mapper","com.excilys.formation.dao"})
public class Config {
	
}
