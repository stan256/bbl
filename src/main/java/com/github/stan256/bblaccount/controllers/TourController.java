package com.github.stan256.bblaccount.controllers;


import com.github.stan256.bblaccount.model.entity.Tour;
import com.github.stan256.bblaccount.model.payload.CreateTourRequest;
import com.github.stan256.bblaccount.service.TourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Transactional
@RequestMapping("/api/tours")
public class TourController {

    private final Validator createTourValidator;
    private final TourService tourService;

    public TourController(@Qualifier("tourValidator") Validator createTourValidator,
                          TourService tourService) {
        this.createTourValidator = createTourValidator;
        this.tourService = tourService;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(createTourValidator);
    }

    @PostMapping(value = "/create")
    public Tour createTour(@Valid CreateTourRequest tour, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            // todo
            throw new RuntimeException("Incorrect tour payload");
        }

        return tourService.saveNewTour(tour);
    }

    @GetMapping(value = "/all")
    public List<Tour> getAllUserTours(Long userId) {
        return tourService.getAllUserTours(userId);
    }
}
