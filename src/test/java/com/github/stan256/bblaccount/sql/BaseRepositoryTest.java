package com.github.stan256.bblaccount.sql;

import com.github.stan256.bblaccount.BblApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("integration-test")
@SpringBootTest(classes = {BblApplication.class, BaseRepositoryTest.DbConfig.class})
public class BaseRepositoryTest {
  @Rule
  @Autowired
  private DbCleanup dbCleanup;

  public void commitAndStartNewTransaction() {
    TestTransaction.flagForCommit();
    TestTransaction.end();
    assertThat(TestTransaction.isActive()).isFalse();
    TestTransaction.start();
  }

  @Slf4j
  @Configuration
  public static class DbConfig implements DisposableBean, InitializingBean {
    private JdbcDatabaseContainer postgres = new PostgreSQLContainer();

    @Bean
    public DataSource getDataSource() {
      DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
      dataSourceBuilder.url(postgres.getJdbcUrl());
      dataSourceBuilder.username(postgres.getUsername());
      dataSourceBuilder.password(postgres.getPassword());
      return dataSourceBuilder.build();
    }

    @Override
    public void afterPropertiesSet() {
      log.info("Starting Container PostgreSQL...");
      postgres.start();
    }

    @Override
    public void destroy() {
      log.info("Stopping Container PostgreSQL...");
      postgres.stop();
    }
  }
}
