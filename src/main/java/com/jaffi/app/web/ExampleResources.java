package com.jaffi.app.web;

import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.collect.Lists;

@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Path("/example")
public class ExampleResources {

	@GET
	public List<String> get() {
		return Lists.newArrayList("Israel", "Argentina", "Australia");
	}
}
