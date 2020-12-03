package com.github.stan256.bblaccount.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor
@Accessors(chain = true)
@Entity(name = "tours")
public class Tour extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tour_id")
    @SequenceGenerator(name = "tour_id", sequenceName = "tours_id_seq", initialValue = 200, allocationSize = 1)
    private Long id;

    @Column(name = "creator_id", nullable = false)
    private long creatorId;

    @Column(name = "tour_name", nullable = false)
    @Size(max = 100)
    private String tourName;

    @Column(name = "min_people_number", nullable = false)
    @Min(1)
    @Max(50)
    private int minPeopleNumber;

    @Column(name = "max_people_number", nullable = false)
    @Min(1)
    @Max(50)
    private int maxPeopleNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tour_id")
    private List<TourTag> tourTags;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tour_id")
    private List<TourRestriction> tourRestrictions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tour_id")
    private List<TourStep> tourSteps;
}
