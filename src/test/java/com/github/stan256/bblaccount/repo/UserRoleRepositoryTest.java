package com.github.stan256.bblaccount.repo;

import com.github.stan256.bblaccount.model.User;
import com.github.stan256.bblaccount.model.UserRole;
import com.github.stan256.bblaccount.sql.BaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserRoleRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    public void exactAmount(){
        List<UserRole> all = userRoleRepository.findAll();
     
    }
}
