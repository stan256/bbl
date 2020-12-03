package com.github.stan256.bblaccount.controllers;


import com.github.stan256.bblaccount.model.entity.TourTag;
import com.github.stan256.bblaccount.service.TourTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Transactional
@RequestMapping("/api/tours/tags")
public class TourTagController {

    private final TourTagService tourTagService;

    public TourTagController(TourTagService tourTagService) {
        this.tourTagService = tourTagService;
    }

    @GetMapping("/all")
    public List<TourTag> getAllTourTags(){
        return tourTagService.findAllTags();
    }

    @GetMapping("/search")
    public List<TourTag> getByStartsWith(String text) {
        return tourTagService.findByStartsWith(text);
    }
}
