package com.example.Cursos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

import com.example.Cursos.Service.FileService;

@SpringBootApplication
public class CursosApplication {
	
/* 	@Resource
	FileService fileService;*/
	public static void main(String[] args) {
		SpringApplication.run(CursosApplication.class, args);
	}
	/*@Override
	public void run(String... arg) throws Exception{
		fileService.deleteAll();
		fileService.init();
	}

  
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
			}
		};
	}*/

}
