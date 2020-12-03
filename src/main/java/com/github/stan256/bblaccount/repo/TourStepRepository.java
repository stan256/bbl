package com.github.stan256.bblaccount.repo;

import com.github.stan256.bblaccount.model.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourStepRepository extends JpaRepository<Tour, Long> {
}
