package com.github.stan256.bblaccount.helpers;

import com.github.stan256.bblaccount.model.payload.RegistrationRequest;
import static com.github.stan256.bblaccount.helpers.UserHelper.TEST_EMAIL;
import static com.github.stan256.bblaccount.helpers.UserHelper.TEST_PASSWORD;

public class AuthHelper {
    public static RegistrationRequest buildTestRegistrationRequest(){
        RegistrationRequest r = new RegistrationRequest();
        r.setEmail(TEST_EMAIL);
        r.setPassword(TEST_PASSWORD);
        return r;
    }
}
