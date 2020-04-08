package com.github.stan256.bblaccount.helpers;

import com.github.stan256.bblaccount.model.payload.RegistrationRequest;

public class AuthHelper {
    public static RegistrationRequest buildTestRegistrationRequest(){
        RegistrationRequest r = new RegistrationRequest();
        r.setEmail("test@email.com");
        r.setPassword("123456supersecret");
        return r;
    }
}
