package com.travel.toy3.domain.itinerary.dto;

import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.entity.Moving;
import com.travel.toy3.domain.itinerary.type.ItineraryType;
import com.travel.toy3.exception.CustomException;
import com.travel.toy3.util.Geocoding;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.IOException;
import java.time.LocalDateTime;

public class CreateMoving {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request {
        @NotNull
        private ItineraryType itineraryType;
        @NotNull
        private String itineraryName;
        @NotNull
        private String vehicle;
        @NotNull
        private String departurePlace;
        @NotNull
        private String destinationPlace;
        @NotNull
        private String departurePlaceAddress; // 출발지 주소
        @NotNull
        private String destinationPlaceAddress; // 도착지 주소
        @NotNull
        private LocalDateTime departureDatetime;
        @NotNull
        private LocalDateTime arrivalDatetime;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private Long tripId;
        private Long itineraryId;
        private ItineraryType itineraryType;
        private String itineraryName;
        private String vehicle;
        private String departurePlace;
        private String destinationPlace;
        private String departurePlaceAddress; // 출발지 주소
        private String destinationPlaceAddress; // 도착지 주소
        private LocalDateTime departureDatetime;
        private LocalDateTime arrivalDatetime;

        public static Response fromEntity(@NonNull Itinerary itinerary, @NonNull Moving moving) throws IOException {
            return Response.builder()
                    .tripId(itinerary.getTrip().getId())
                    .itineraryId(itinerary.getId())
                    .itineraryType(itinerary.getItineraryType())
                    .itineraryName(itinerary.getItineraryName())
                    .vehicle(moving.getVehicle())
                    .departurePlace(moving.getDeparturePlace())
                    .destinationPlace(moving.getDestinationPlace())
                    .departurePlaceAddress(Geocoding.getAddress(moving.getDeparturePlaceLatitude(), moving.getDeparturePlaceLongitude()))
                    .destinationPlaceAddress(Geocoding.getAddress(moving.getDestinationPlaceLatitude(), moving.getDestinationPlaceLongitude()))
                    .departureDatetime(moving.getDepartureDatetime())
                    .arrivalDatetime(moving.getArrivalDatetime())
                    .build();
        }
    }
}
