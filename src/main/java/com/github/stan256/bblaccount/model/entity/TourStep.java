package com.github.stan256.bblaccount.model.entity;

import com.github.stan256.bblaccount.model.TravelMode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor
@Accessors(chain = true)
@Entity(name = "tour_steps")
public class TourStep extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tour_step_id")
    @SequenceGenerator(name = "tour_step_id", sequenceName = "tour_step_id_seq", initialValue = 200, allocationSize = 1)
    private Long id;

    @Column(name = "location", nullable = false)
    @Size(max = 200)
    private String location;

    @Column(name = "description")
    @Size(max = 300)
    private String description;

    @Column(name = "show_route_to_next_point", nullable = false)
    private boolean showRouteToNext;

    @Column(name = "travel_mode_to_next_point")
    @Enumerated(value = EnumType.STRING)
    private TravelMode travelModeToNext;

    @Column(name = "location_lat", nullable = false)
    @Min(-90)
    @Max(90)
    private float locationLat;

    @Column(name = "location_lng", nullable = false)
    @Min(-180)
    @Max(180)
    private float locationLng;

    // todo image
//    String images: Array<ImageSelection>;
}
