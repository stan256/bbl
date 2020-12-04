package com.github.stan256.bblaccount.repo;

import com.github.stan256.bblaccount.model.entity.TourTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TourTagRepository extends JpaRepository<TourTag, Long> {
    Optional<TourTag> findByTag(String tag);

    @Query("select t from tour_tags t where LOWER(t.tag) LIKE LOWER(concat(?1, '%'))")
    List<TourTag> findByStartsWith(String text);
}
