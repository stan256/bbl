package com.github.stan256.bblaccount.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "product_category")
public class ProductCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_cat_id")
  @SequenceGenerator(name = "prod_cat_id", sequenceName = "product_category_id_seq", initialValue = 0, allocationSize = 1)
  private long id;
  @Column
  private String name;
}

