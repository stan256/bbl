package com.github.stan256.bblaccount.model.payload;

import com.github.stan256.bblaccount.model.entity.TourRestriction;
import com.github.stan256.bblaccount.model.entity.TourTag;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateTourRequest {
    @NotNull
    @Positive
    private Long creatorId;

    @NotNull
    @Size(min = 5, max = 200)
    private String tourName;

    @Min(1)
    @Max(50)
    @NotNull
    private Integer minPeopleNumber;

    @Min(1)
    @Max(50)
    @NotNull
    private Integer maxPeopleNumber;

    @NotNull
    private List<TourStepRequest> tourSteps;

    private List<TourTag> tourTags;
    private List<TourRestriction> tourRestrictions;
}
