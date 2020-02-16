package com.github.stan256.bblaccount.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "product")
public class Product {
  @Id
  @GeneratedValue
  private long id;
  private String name;
  private double price;
  private LocalDateTime lastUpdated;
  @OneToOne
  private ProductCategory productCategory;
}
