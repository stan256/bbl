package com.github.stan256.bblaccount.helpers;

import com.github.stan256.bblaccount.model.entity.User;
import com.github.stan256.bblaccount.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class UserHelper {
    public static User buildTestUser() {
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
    
    public static User buildTestUser(String email, String password){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
