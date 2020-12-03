package com.github.stan256.bblaccount.service;

import com.github.stan256.bblaccount.model.entity.Tour;
import com.github.stan256.bblaccount.model.entity.TourStep;
import com.github.stan256.bblaccount.model.payload.CreateTourRequest;
import com.github.stan256.bblaccount.model.payload.TourStepRequest;
import com.github.stan256.bblaccount.repo.TourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TourService {

    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public Tour saveNewTour(CreateTourRequest tour) {
        Tour dbTour = new Tour()
                .setCreatorId(tour.getCreatorId())
                .setTourName(tour.getTourName())
                .setMinPeopleNumber(tour.getMinPeopleNumber())
                .setMaxPeopleNumber(tour.getMaxPeopleNumber())
                .setTourTags(tour.getTourTags())
                .setTourRestrictions(tour.getTourRestrictions())
                .setTourSteps(convertSteps(tour.getTourSteps()));

        return tourRepository.save(dbTour);
    }

    private List<TourStep> convertSteps(List<TourStepRequest> tourSteps) {
        return tourSteps.stream()
                .map(tourStepRequest -> new TourStep()
                        .setLocation(tourStepRequest.getLocation())
                        .setDescription(tourStepRequest.getDescription())
                        .setShowRouteToNext(tourStepRequest.getShowRouteToNext())
                        .setTravelModeToNext(tourStepRequest.getTravelModeToNext())
                        .setLocationLat(tourStepRequest.getLocationLat())
                        .setLocationLng(tourStepRequest.getLocationLng()))
                .collect(Collectors.toList());
    }

    public List<Tour> getAllUserTours(Long userId) {
        return tourRepository.findAllByCreatorId(userId);
    }
}
