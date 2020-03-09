package com.github.stan256.bblaccount.sql;

import lombok.extern.slf4j.Slf4j;
import org.junit.rules.ExternalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DbCleanup extends ExternalResource {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void cleanup() {
        jdbcTemplate.execute("DELETE FROM users");
        jdbcTemplate.execute("DELETE FROM product");
    }
}
