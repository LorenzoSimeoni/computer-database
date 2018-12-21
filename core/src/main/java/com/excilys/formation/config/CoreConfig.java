package com.excilys.formation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {ServiceConfig.class})
@ComponentScan(basePackages = { "com.excilys.formation.checker", 
		"com.excilys.formation.exception", 
		"com.excilys.formation.model", 
		"com.excilys.formation.validator" })
public class CoreConfig {

}
