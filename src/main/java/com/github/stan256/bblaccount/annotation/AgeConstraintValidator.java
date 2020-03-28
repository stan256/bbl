package com.github.stan256.bblaccount.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeConstraintValidator implements ConstraintValidator<Age, Integer> {

    @Override
    public void initialize(Age parameters) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        try {
            return value > 0 && value < 150;
        } catch (ClassCastException e){
            return false;
        }
    }
}
