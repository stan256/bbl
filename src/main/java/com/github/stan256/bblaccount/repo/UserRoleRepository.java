package com.github.stan256.bblaccount.repo;

import com.github.stan256.bblaccount.model.User;
import com.github.stan256.bblaccount.model.UserRole;
import com.github.stan256.bblaccount.model.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.Optional;

@RepositoryRestController
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRole(UserRoleEnum role);
}
