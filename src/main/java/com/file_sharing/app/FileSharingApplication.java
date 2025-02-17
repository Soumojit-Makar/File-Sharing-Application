package com.file_sharing.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "File Sharing App API", version = "1.0.0", description = "API for files sharing"))
public class FileSharingApplication  {

	public static void main(String[] args) {
		SpringApplication.run(FileSharingApplication.class, args);
	}

}
