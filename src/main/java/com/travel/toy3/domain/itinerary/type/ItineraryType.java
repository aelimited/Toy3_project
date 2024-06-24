package com.travel.toy3.domain.itinerary.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItineraryType {
    MOVING("이동"),
    ACCOMMODATION("숙박"),
    STAY("체류");

    private final String korean;
}
