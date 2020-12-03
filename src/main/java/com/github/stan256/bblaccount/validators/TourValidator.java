package com.github.stan256.bblaccount.validators;

import com.github.stan256.bblaccount.model.payload.CreateTourRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.util.Set;

@Service
public class TourValidator implements Validator {

    private final Validator tourStepValidator;
    // todo way to validate without
    private final javax.validation.Validator javaValidator;

    public TourValidator(
            @Qualifier("tourStepValidator") Validator tourStepValidator,
            javax.validation.Validator javaValidator
    ) {
        this.tourStepValidator = tourStepValidator;
        this.javaValidator = javaValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CreateTourRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        CreateTourRequest p = (CreateTourRequest) obj;

        // javax validation
        Set<ConstraintViolation<CreateTourRequest>> constraintViolations = javaValidator.validate(p);
        for (ConstraintViolation<CreateTourRequest> constraintViolation : constraintViolations) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.rejectValue(propertyPath, "", message);
        }

        // validating tour steps
        if (p.getTourSteps() == null || p.getTourSteps().isEmpty()) {
            errors.rejectValue("tourSteps", "Tour steps can't be empty");
        } else {
            p.getTourSteps().forEach(tourStepRequest -> {
                ValidationUtils.invokeValidator(tourStepValidator, tourStepRequest, errors);
            });
        }
    }
}
