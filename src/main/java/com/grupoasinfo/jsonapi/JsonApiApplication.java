package com.grupoasinfo.jsonapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.grupoasinfo.jsonapi")
//@EnableSwagger2
public class JsonApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonApiApplication.class, args);
	}

}
