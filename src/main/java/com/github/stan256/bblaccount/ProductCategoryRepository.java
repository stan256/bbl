package com.github.stan256.bblaccount;

import com.github.stan256.bblaccount.model.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {
  List<ProductCategory> findAll();
}
