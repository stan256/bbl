package com.github.stan256.bblaccount.service;

import com.github.stan256.bblaccount.model.RoleName;
import com.github.stan256.bblaccount.model.entity.Role;
import com.github.stan256.bblaccount.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> findUserRole() {
        return roleRepository.findByRole(RoleName.ROLE_USER);
    }
}
