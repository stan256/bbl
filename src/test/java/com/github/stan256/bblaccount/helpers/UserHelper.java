package com.github.stan256.bblaccount.helpers;

import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class UserHelper {
    public static final String TEST_EMAIL = "test@email.com";
    public static final String TEST_PASSWORD = "123456supersecret";

    public static User buildTestUser() {
        User user = new User();
        user.setFirstName("Stanislav");
        user.setLastName("Studzinskyi");
        user.setEmail(TEST_EMAIL);
        user.setPassword(TEST_PASSWORD);
        user.setAge(25);
        user.setEmailVerified(false);
        user.setActive(true);
        return user;
    }
    
    public static User buildTestUser(String email, String password){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
