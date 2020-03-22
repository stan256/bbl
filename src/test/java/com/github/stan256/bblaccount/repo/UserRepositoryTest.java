package com.github.stan256.bblaccount.repo;

import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.model.entity.Role;
import com.github.stan256.bblaccount.model.RoleName;
import com.github.stan256.bblaccount.sql.BaseRepositoryTest;
import com.github.stan256.bblaccount.util.UserHelper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class UserRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void exactAmount(){
        User user1 = userHelper.buildTestUser();
        User user2 = userHelper.buildTestUser();
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertEquals(2, users.size());
        assertEquals(3, roleRepository.findAll().size());

        System.out.println(user1.getId());

        userRepository.deleteById(user1.getId());
        userRepository.deleteById(user2.getId());
        assertEquals(0, users.size());
        assertEquals(3, roleRepository.findAll().size());
    }
}
