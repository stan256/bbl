package com.github.stan256.bblaccount.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "users")
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id")
  @SequenceGenerator(name = "user_id", sequenceName = "users_id_seq", initialValue = 0, allocationSize = 1)
  private long id;
  @Column
  private String firstName;
  @Column
  private String lastName;
  @Column
  private int age;
  private LocalDateTime lastUpdated = LocalDateTime.now();
}
