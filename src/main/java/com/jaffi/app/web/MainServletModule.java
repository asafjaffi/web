package com.jaffi.app.web;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class MainServletModule extends ServletModule {
    @Override
    protected void configureServlets() {

        // hook Jersey into Guice Servlet
        bind(GuiceContainer.class);

        // hook Gson into Jersey as the POJO <-> JSON mapper
        bind(GsonJsonProvider.class).in(Scopes.SINGLETON);

        // serve
        Map<String, String> params = new HashMap<String, String>();
        params.put("com.sun.jersey.config.property.WebPageContentRegex", "index");
        
        filter("/*").through(GuiceContainer.class, params);
//        filter("/*").through(GuiceContainer.class);
    }
}