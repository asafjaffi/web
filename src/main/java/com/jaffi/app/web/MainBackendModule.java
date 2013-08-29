package com.jaffi.app.web;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Module;

public class MainBackendModule extends AbstractModule {

	@Override
	public void configure() {
		bind(ExampleResources.class);
		
		install(new MainServletModule());
	}

}
