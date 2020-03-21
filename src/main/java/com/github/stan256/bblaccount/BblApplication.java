package com.github.stan256.bblaccount;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// docker run -d -e POSTGRES_USER=bbl -e POSTGRES_PASSWORD=bbl --name bbl -p 1717:5432 postgres

@SpringBootApplication
public class BblApplication {


  public static void main(String[] args) {
    SpringApplication.run(BblApplication.class, args);
  }
}
