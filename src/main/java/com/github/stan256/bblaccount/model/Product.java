package com.github.stan256.bblaccount.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "product")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod_id")
  @SequenceGenerator(name = "prod_id", sequenceName = "product_id_seq", initialValue = 0, allocationSize = 1)
  private long id;
  @Column
  private String name;
  @Column
  private double price;
  @Column
  private LocalDateTime lastUpdated;
  @OneToOne
  private ProductCategory productCategory;
}
