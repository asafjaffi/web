package com.jaffi.app.web;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.EnumSet;

public class HttpServerMain {
    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                binder().requireExplicitBindings();

                install(new MainBackendModule());

                bind(GuiceFilter.class);
            }
        });


        Server server = new Server(8080);

        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/");
        handler.setResourceBase(HttpServerMain.class.getClassLoader().getResource("webapp").toExternalForm());

//        handler.addServlet(new ServletHolder(new InvalidRequestServlet()), "/*");

        FilterHolder guiceFilter = new FilterHolder(injector.getInstance(GuiceFilter.class));
        handler.addFilter(guiceFilter, "/*", EnumSet.allOf(DispatcherType.class));

        handler.addServlet(DefaultServlet.class, "/");
        
        server.setHandler(handler);
        server.start();
    }
}