package com.github.stan256.bblaccount.service;

import com.github.stan256.bblaccount.model.entity.TourRestriction;
import com.github.stan256.bblaccount.model.payload.CreateTourRestrictionRequest;
import com.github.stan256.bblaccount.repo.TourRestrictionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TourRestrictionService {

    private final TourRestrictionRepository tourRestrictionRepository;

    public TourRestrictionService(TourRestrictionRepository tourRestrictionRepository) {
        this.tourRestrictionRepository = tourRestrictionRepository;
    }

    public TourRestriction saveRestriction(CreateTourRestrictionRequest createRestriction) {
        Optional<TourRestriction> oRestriction = tourRestrictionRepository
                .findByRestriction(createRestriction.getRestriction());

        return oRestriction
                .orElseGet(() -> tourRestrictionRepository.save(new TourRestriction(createRestriction.getRestriction())));
    }

    public List<TourRestriction> findAll() {
        return tourRestrictionRepository.findAll();
    }

    public List<TourRestriction> findByStartsWith(String text) {
        return tourRestrictionRepository.findByStartsWith(text);
    }
}
