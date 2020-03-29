package com.github.stan256.bblaccount.util;

import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class UserHelper {
    @Autowired
    private RoleRepository roleRepository;

    public User buildTestUser() {
        User user = new User();
        user.setFirstName("Stanislav");
        user.setLastName("Studzinskyi");
        user.setEmail("test@email.com");
        user.setPassword("1234test");
        user.setAge(25);
        user.setEmailVerified(false);
        user.setActive(true);
        return user;
    }
}
