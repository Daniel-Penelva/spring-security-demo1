package com.apirest.springsecuritydemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

	/* Cross Origin - Configuração centralizada */
	@Override
	public void addCorsMappings(CorsRegistry registry) {

		// libera acesso a todos os endpoints do usuario do RestController "IndexController" e todos os métodos (POST, GET, PUT, DELETE). E liberar acesso a todos os servidores que poderá fazer a requisição.
		registry.addMapping("/usuario/**")
                        .allowedOrigins("*") // Permit all origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow common HTTP methods
                        .allowedHeaders("*"); // Permit all headers

		// OBS. Caso queira liberar para um unico servidor para que possa fazer a requisição basta colocar "http://localhost:8080" - exemplo: .allowedOrigins("http://localhost:8080") 
	}

}
