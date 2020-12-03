package com.github.stan256.bblaccount.controllers;


import com.github.stan256.bblaccount.model.entity.TourRestriction;
import com.github.stan256.bblaccount.service.TourRestrictionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Transactional
@RequestMapping("/api/tours/restrictions")
public class TourRestrictionController {

    private final TourRestrictionService tourRestrictionService;

    public TourRestrictionController(TourRestrictionService tourRestrictionService) {
        this.tourRestrictionService = tourRestrictionService;
    }

    @GetMapping("/all")
    public List<TourRestriction> getAllRestrictions(){
        return tourRestrictionService.findAll();
    }

    @GetMapping("/search")
    public List<TourRestriction> getByStartsWith(String text) {
        return tourRestrictionService.findByStartsWith(text);
    }
}
