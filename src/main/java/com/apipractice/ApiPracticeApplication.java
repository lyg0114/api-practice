package com.apipractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ApiPracticeApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiPracticeApplication.class, args);
  }
}
