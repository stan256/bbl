package com.github.stan256.bblaccount.model.entity;

import com.github.stan256.bblaccount.model.RoleName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor
@Entity(name = "roles")
public class Role extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id")
    @SequenceGenerator(name = "role_id", sequenceName = "roles_id_seq", initialValue = 200, allocationSize = 1)
    private Long id;

    @Column(unique = true)
    @NaturalId
    @Enumerated(EnumType.STRING)
    private RoleName role;

    public Role(RoleName user) {
        this.role = user;
    }

    public boolean isAdminRole() {
        return this.role.equals(RoleName.ROLE_ADMIN);
    }
}
