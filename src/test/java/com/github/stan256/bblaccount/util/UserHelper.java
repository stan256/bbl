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
        User stas = new User();
        stas.setFirstName("Stanislav");
        stas.setLastName("Studzinskyi");
        stas.setEmail("test@email.com");
        stas.setPassword("1234test");
        stas.setAge(25);
        stas.setEmailVerified(false);
        stas.setActive(true);
        return stas;
    }
}
