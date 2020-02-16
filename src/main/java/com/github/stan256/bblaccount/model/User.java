package com.github.stan256.bblaccount.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "user")
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue
  private long id;
  private String firstName;
  private String lastName;
  private int age;
  private LocalDateTime lastUpdated;
  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(
      name = "user_product",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "product_id")}
  )
  List<Product> products;
}
