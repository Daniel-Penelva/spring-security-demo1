package com.apirest.springsecuritydemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EntityScan(basePackages = {"com.apirest.springsecuritydemo1.entities"})
@EnableJpaRepositories(basePackages = {"com.apirest.springsecuritydemo1.repositories"})
@ComponentScan(basePackages = {"com.*"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
public class SpringSecurityDemo1Application implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemo1Application.class, args);
	}

}
