package com.github.stan256.bblaccount.repo;

import com.github.stan256.bblaccount.model.entity.TourRestriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TourRestrictionRepository extends JpaRepository<TourRestriction, Long> {
    Optional<TourRestriction> findByRestriction(String restriction);

    @Query("select t from tour_restrictions t where LOWER(t.restriction) LIKE LOWER(concat(?1, '%'))")
    List<TourRestriction> findByStartsWith(String text);
}
