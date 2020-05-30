package com.github.stan256.bblaccount.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
   public void initialize(ValidPassword constraint) {
   }

   public boolean isValid(String obj, ConstraintValidatorContext context) {
      /*

      ^                 # start-of-string
      (?=.*[0-9])       # a digit must occur at least once
      (?=.*[a-zA-Z])    # a letter must occur at least once
      (?=\S+$)          # no whitespace allowed in the entire string
      .{6,}             # anything, at least eight places though
      $                 # end-of-string

      * */
      return obj.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,}$");
   }
}
