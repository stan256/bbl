package com.github.stan256.bblaccount.repo;

import com.github.stan256.bblaccount.model.User;
import com.github.stan256.bblaccount.model.UserRole;
import com.github.stan256.bblaccount.model.UserRoleEnum;
import com.github.stan256.bblaccount.sql.BaseRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class UserRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void exactAmount(){
        User stas = new User();
        stas.setFirstName("Stanislav");
        stas.setLastName("Studzinskyi");
        stas.setEmail("test@email.com");
        stas.setPassword("s");
        stas.setUserRoles(Arrays.asList(new UserRole(UserRoleEnum.USER)));
        stas.setAge(25);

        User nellia = new User();
        nellia.setFirstName("Nellia");
        nellia.setLastName("Salimova");
        nellia.setEmail("teste@mail.com");
        nellia.setPassword("s");
        nellia.setUserRoles(Arrays.asList(new UserRole(UserRoleEnum.USER)));
        nellia.setAge(25);

        userRepository.save(stas);
        userRepository.save(nellia);
        List<User> all = userRepository.findAll();
        assertEquals(2, all.size());
    }
}
