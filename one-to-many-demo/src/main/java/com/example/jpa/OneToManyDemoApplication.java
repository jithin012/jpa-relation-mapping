package com.example.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OneToManyDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneToManyDemoApplication.class, args);
	}

}

//ref : https://github.com/callicoder/jpa-hibernate-tutorials
//https://www.callicoder.com/hibernate-spring-boot-jpa-one-to-many-mapping-example/

//two tables - posts and comments of a Blog database schema where the posts table has a one-to-many relationship with the comments table
//A POST CAN BE MULTIPLE COMMENTS
