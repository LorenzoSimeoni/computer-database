package com.excilys.formation.cli;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.excilys.formation.model.Computer;

public class EntryPoint {
 
	private static final String REST_URI = "http://localhost:8080/webapp/Computer-rest/13";

	public Computer getJsonEmployee() {
		Client client = ClientBuilder.newClient().register(new Authenticator("Lorenzo", "mdp"));
		return client
				.target(REST_URI)
				.request(MediaType.APPLICATION_JSON)
				.get(Computer.class);
	}
}