package com.github.stan256.bblaccount.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "roles")
@NoArgsConstructor
public class UserRole extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id")
    @SequenceGenerator(name = "role_id", sequenceName = "roles_id_seq", initialValue = 0, allocationSize = 1)
    private long id;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public UserRole(UserRoleEnum user) {
        this.role = user;
    }
}
