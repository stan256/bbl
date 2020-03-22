package com.github.stan256.bblaccount.repo;

import com.github.stan256.bblaccount.model.RoleName;
import com.github.stan256.bblaccount.model.entity.Role;
import com.github.stan256.bblaccount.sql.BaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class RoleRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void exactAmount(){
        List<RoleName> db = roleRepository.findAll().stream().map(Role::getRole).collect(Collectors.toList());
        List<RoleName> roleNames = Arrays.asList(RoleName.values());
        assertTrue(db.size() == roleNames.size() && db.containsAll(roleNames) && roleNames.containsAll(db));
    }
}
