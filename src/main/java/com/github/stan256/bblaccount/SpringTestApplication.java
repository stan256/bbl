package com.github.stan256.bblaccount;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories
public class SpringTestApplication {
  @Autowired
  public ProductCategoryRepository productCategoryRepository;

  @Bean
  public CommandLineRunner runner() {
    return (a) -> {
      log.warn(String.valueOf(productCategoryRepository.findAll()));
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringTestApplication.class, args);
  }
}
