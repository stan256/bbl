package com.github.stan256.bblaccount.service;

import com.github.stan256.bblaccount.model.entity.TourTag;
import com.github.stan256.bblaccount.model.payload.CreateTourTagRequest;
import com.github.stan256.bblaccount.repo.TourTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TourTagService {

    private final TourTagRepository tourTagRepository;

    public TourTagService(TourTagRepository tourTagRepository) {
        this.tourTagRepository = tourTagRepository;
    }

    public TourTag saveTag(CreateTourTagRequest tag) {
        Optional<TourTag> oTag = tourTagRepository.findByTag(tag.getTag());
        return oTag.orElseGet(() -> tourTagRepository.save(new TourTag(tag.getTag())));
    }

    public List<TourTag> findAllTags() {
        return tourTagRepository.findAll();
    }

    public List<TourTag> findByStartsWith(String text) {
        return tourTagRepository.findByStartsWith(text);
    }
}
