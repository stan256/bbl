package com.github.stan256.bblaccount.model.entity;

import com.github.stan256.bblaccount.annotation.Age;
import com.github.stan256.bblaccount.annotation.ValidPassword;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor
@Entity(name = "users")
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id")
    @SequenceGenerator(name = "user_id", sequenceName = "users_id_seq", initialValue = 0, allocationSize = 1)
    private long id;

    @Column
    @Size(max = 30)
    private String firstName;

    @Column
    @Size(max = 30)
    private String lastName;

    @Column(nullable = false, unique = true)
    @Size(max = 50)
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false)
    @Size(min = 6, max = 60)
    @NotBlank
    @ValidPassword
    private String password;

    @Column(nullable = false)
    @Age
    private Integer age;

    @Column(nullable = false)
    private Boolean emailVerified;

    @Column(nullable = false)
    private Boolean active;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles;
}

