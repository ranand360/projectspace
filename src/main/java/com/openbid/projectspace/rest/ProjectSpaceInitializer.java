package com.openbid.projectspace.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 
 * @author Anand Raju
 * Main initializer to spin up spring boot application for Project Space 
 */

@SpringBootApplication
@EnableScheduling
public class ProjectSpaceInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProjectSpaceInitializer.class);
	}

	/**
	 * Main method to bootsrap spring application
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProjectSpaceInitializer.class, args);
	}

}