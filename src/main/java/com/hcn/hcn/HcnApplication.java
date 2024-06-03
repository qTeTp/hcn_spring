package com.hcn.hcn;

import org.hibernate.annotations.Comment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.hcn.hcn.model")
@EnableJpaRepositories("com.hcn.hcn.repository")
public class HcnApplication {
	public static void main(String[] args) {
		SpringApplication.run(HcnApplication.class, args);
	}
}
