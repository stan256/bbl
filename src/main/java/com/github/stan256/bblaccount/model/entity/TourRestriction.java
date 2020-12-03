package com.github.stan256.bblaccount.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor
@Entity(name = "tour_restrictions")
public class TourRestriction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tour_restriction_id")
    @SequenceGenerator(name = "tour_restriction_id", sequenceName = "tour_restriction_id_seq", initialValue = 200, allocationSize = 1)
    private Long id;

    @NaturalId
    @Column(name = "restriction", unique = true, nullable = false)
    @Size(max = 100)
    private String restriction;

    public TourRestriction(String restriction) {
        this.restriction = restriction;
    }
}
