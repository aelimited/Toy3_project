package com.travel.toy3.domain.itinerary.dto;

import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.entity.Moving;
import com.travel.toy3.domain.itinerary.type.ItineraryType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MovingDTO {
    private String vehicle;
    private String departurePlace;
    private String destinationPlace;
    private String departurePlaceAddress; // 출발지 주소
    private String destinationPlaceAddress; // 도착지 주소
    private LocalDateTime departureDatetime;
    private LocalDateTime arrivalDatetime;
}
