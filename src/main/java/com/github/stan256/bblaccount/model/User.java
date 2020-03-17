package com.github.stan256.bblaccount.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "users")
@NoArgsConstructor
public class User extends DateAudit{
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
  @Email
  private String email;

  @Column
  @Size(max = 40)
  @NotBlank
  private String password;

  @Column
  @Max(150)
  @Min(0)
  private int age;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private List<UserRole> userRoles;
}

