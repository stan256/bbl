package com.github.stan256.bblaccount.repo;

import com.github.stan256.bblaccount.model.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findAllByCreatorId(long id);
}
