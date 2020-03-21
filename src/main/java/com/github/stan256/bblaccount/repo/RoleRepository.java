package com.github.stan256.bblaccount.repo;

import com.github.stan256.bblaccount.model.entity.Role;
import com.github.stan256.bblaccount.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.Optional;

@RepositoryRestController
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleName role);
}
