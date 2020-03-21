package com.github.stan256.bblaccount.service;

import com.github.stan256.bblaccount.model.entity.Role;
import com.github.stan256.bblaccount.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;


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
}
