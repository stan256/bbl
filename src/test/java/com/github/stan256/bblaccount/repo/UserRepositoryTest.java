package com.github.stan256.bblaccount.repo;

import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.sql.BaseRepositoryTest;
import com.github.stan256.bblaccount.util.UserHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class UserRepositoryTest extends BaseRepositoryTest {
    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void addAndDeleteUsers() {
        User user1 = userRepository.save(userHelper.buildTestUser());
        User user2 = userRepository.save(userHelper.buildTestUser());

        assertEquals(2, userRepository.findAll().size());
        assertEquals(3, roleRepository.findAll().size());
        userRepository.delete(user1);
        userRepository.deleteById(user2.getId());
        assertEquals(0, userRepository.findAll().size());
        assertEquals(3, roleRepository.findAll().size());
    }

    @Test
    public void buSureDbIsCleanAfterFirstTest() {
        assertEquals(0, userRepository.findAll().size());
        assertEquals(3, roleRepository.findAll().size());
    }

    @Test
    public void shouldThrowValidationExceptionOnIncorrectEmail() {
        User user = userHelper.buildTestUser();
        user.setEmail("incorrect");
        assertThrows(TransactionSystemException.class, () -> userRepository.save(user));
        user.setEmail("good@email.com");
        userRepository.save(user);
        assertEquals(1, userRepository.findAll().size());
    }
}
