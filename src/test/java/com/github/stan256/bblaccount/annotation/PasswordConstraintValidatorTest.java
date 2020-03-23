package com.github.stan256.bblaccount.annotation;

import com.github.stan256.bblaccount.BblApplication;
import com.github.stan256.bblaccount.sql.BaseRepositoryTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("integration-test")
@SpringBootTest(classes = {BblApplication.class, BaseRepositoryTest.DbConfig.class})
class PasswordConstraintValidatorTest {
    PasswordConstraintValidator validator = new PasswordConstraintValidator();

    @Test
    private void validMethod(){
        Assert.assertFalse(validator.isValid("incorrect", null));
        Assert.assertFalse(validator.isValid("123456", null));
        Assert.assertFalse(validator.isValid("t1", null));
        Assert.assertTrue(validator.isValid("normalPassword1234", null));
    }

}
