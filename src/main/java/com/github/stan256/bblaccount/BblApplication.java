package com.github.stan256.bblaccount;

import com.github.stan256.bblaccount.repo.ProductCategoryRepository;
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
public class BblApplication {
  @Autowired
  public ProductCategoryRepository productCategoryRepository;

  public static void main(String[] args) {
    SpringApplication.run(BblApplication.class, args);
  }
}
