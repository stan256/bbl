package com.github.stan256.bblaccount.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "users")
@NoArgsConstructor
public class User extends DateAudit {
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

  @Column(nullable = false, unique = true)
  @Size(max = 50)
  @NotBlank
  @Email
  private String email;

  @Column(nullable = false)
  @Size(max = 60)
  @NotBlank
  private String password;

  @Column(nullable = false)
  @Max(150)
  @Min(1)
  private int age;

  @Column(nullable = false)
  private Boolean emailVerified;

  @Column(nullable = false)
  private Boolean active;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles;
}

