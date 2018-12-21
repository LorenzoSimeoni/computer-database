package com.excilys.formation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {CoreConfig.class, BindingConfig.class})
@ComponentScan(basePackages = { "com.excilys.formation.controller" })
public class WebAppConfig {

}
