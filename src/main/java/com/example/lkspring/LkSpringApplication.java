package com.example.lkspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.lkspring.repository")
@EntityScan("com.example.lkspring.model")
@ComponentScan("com.example.lkspring")
public class LkSpringApplication extends SpringBootServletInitializer {

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LkSpringApplication.class);
	}*/
	public static void main(String[] args) {
		SpringApplication.run(LkSpringApplication.class, args);
	}
}
