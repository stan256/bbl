package com.github.stan256.bblaccount;

import com.github.stan256.bblaccount.model.ProductCategory;
import com.github.stan256.bblaccount.sql.BaseRepositoryTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class ProductCategoryRepositoryTest extends BaseRepositoryTest {

  @Autowired
  private ProductCategoryRepository productCategoryRepository;

  @Test
  void contextLoads() {
    log.error(String.valueOf(productCategoryRepository.findAll()));
  }
}
