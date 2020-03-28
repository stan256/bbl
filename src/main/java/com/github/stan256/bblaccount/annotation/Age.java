package com.github.stan256.bblaccount.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = AgeConstraintValidator.class)
public @interface Age {
    String message() default "Invalid Age";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
