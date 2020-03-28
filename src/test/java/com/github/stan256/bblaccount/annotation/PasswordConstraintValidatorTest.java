package com.github.stan256.bblaccount.annotation;

import com.github.stan256.bblaccount.BblApplication;
import com.github.stan256.bblaccount.sql.BaseRepositoryTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

class PasswordConstraintValidatorTest {
    PasswordConstraintValidator validator = new PasswordConstraintValidator();

    @Test
    private void validMethod(){
        assertFalse(validator.isValid("incorrect", null));
        assertFalse(validator.isValid("123456", null));
        assertFalse(validator.isValid("t1", null));
        assertTrue(validator.isValid("normalPassword1234", null));
    }

}
