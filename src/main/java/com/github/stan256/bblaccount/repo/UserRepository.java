package com.github.stan256.bblaccount.repo;

import com.github.stan256.bblaccount.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.Optional;

@RepositoryRestController
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
