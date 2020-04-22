package com.github.stan256.bblaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// docker stop bbl & docker rm bbl & docker run -d -e POSTGRES_USER=bbl -e POSTGRES_PASSWORD=bbl --name bbl -p 1717:5432 postgres

@SpringBootApplication
public class BblApplication {
    public static void main(String[] args) {
        SpringApplication.run(BblApplication.class, args);
    }
}
