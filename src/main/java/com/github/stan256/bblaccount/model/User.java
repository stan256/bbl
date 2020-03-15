package com.github.stan256.bblaccount.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity(name = "users")
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id")
  @SequenceGenerator(name = "user_id", sequenceName = "users_id_seq", initialValue = 0, allocationSize = 1)
  private long id;

  @Column
  @Size(max = 30)
  @NotBlank
  private String firstName;

  @Column
  @Size(max = 30)
  @NotBlank
  private String lastName;

  @Column
  @Size(max = 50)
  @NotBlank
  private String email;

  @Column
  @Size(max = 40)
  @NotBlank
  private String password;

  @Column
  @Max(150)
  @Min(0)
  private int age;

  @ManyToMany
  @JoinTable(name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<UserRole> userRoles;

  @Column
  @Past
  private LocalDateTime lastUpdated = LocalDateTime.now();
}
