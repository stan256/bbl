package com.github.stan256.bblaccount.validators;

import com.github.stan256.bblaccount.model.payload.TourStepRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class TourStepValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return TourStepRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        TourStepRequest p = (TourStepRequest) obj;

        if (p.getShowRouteToNext() && p.getTravelModeToNext() == null){
            errors.rejectValue("travelModeToNext", "Travel mode can't be not set if show route to next point is enabled");
        }
    }
}
