package com.excilys.formation.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.excilys.formation.service.UsersService;

@Configuration
@EnableWebSecurity
@Import(value = { BindingConfig.class, ServiceConfig.class })
@ComponentScan(basePackages = { "com.excilys.formation.controller" })
public class WebAppConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsersService userDetailsService;
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	  throws Exception {
	    auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	public void configure(WebSecurity web)
	  throws Exception {
	   web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
			.antMatchers("/Computer/update").hasRole("ADMIN")
			.antMatchers("/Computer/add").hasRole("ADMIN")
			.antMatchers("/Computer/").hasRole("USER,ADMIN")
			.antMatchers("/").permitAll()
		.anyRequest().authenticated()	
		.and()
		.formLogin()
		.and()
		.httpBasic()
		.and()
		.logout().permitAll();
	}

}