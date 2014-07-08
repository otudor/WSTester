package com.wstester.server;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

import org.glassfish.grizzly.http.server.HttpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
@EnableAutoConfiguration
public class Main {

    private static URI getRestBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(9998).build();
    }

    public static final URI BASE_URI = getRestBaseURI();

    public static HttpServer startRestServer() throws IOException {
        System.out.println("Starting Rest with grizzly...");
        ResourceConfig rc = new PackagesResourceConfig("com.wstester.server");
        return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
    }
    
    public static ConfigurableApplicationContext startSOAPServer() throws IOException {
    	System.out.println("Starting SOAP with Spting...");
    	return SpringApplication.run(WeatherConfiguration.class, "");
    }
}