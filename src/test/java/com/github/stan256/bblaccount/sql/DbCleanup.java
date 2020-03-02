package com.github.stan256.bblaccount.sql;

import org.junit.rules.ExternalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbCleanup extends ExternalResource {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected void after() {
        super.after();
        cleanup();
    }

    private void cleanup() {
        jdbcTemplate.execute("DELETE FROM users");
        jdbcTemplate.execute("DELETE FROM product");
    }
}
