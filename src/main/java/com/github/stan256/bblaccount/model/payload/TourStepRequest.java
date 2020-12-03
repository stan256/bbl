package com.github.stan256.bblaccount.model.payload;

import com.github.stan256.bblaccount.model.TravelMode;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class TourStepRequest {
    @NotNull
    @Size(min = 1, max = 200)
    private String location;

    @Size(max = 300)
    private String description;

    @NotNull
    private Boolean showRouteToNext;

    @Min(-90)
    @Max(90)
    @NotNull
    private Float locationLat;

    @Min(-180)
    @Max(180)
    @NotNull
    private Float locationLng;

    private TravelMode travelModeToNext;
}
