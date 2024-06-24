package com.travel.toy3.domain.itinerary.dto;


import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.type.ItineraryType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class CreateItinerary {
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

        public static Response fromEntity(@NonNull Itinerary itinerary) {
            return Response.builder()
                    .tripId(itinerary.getTrip().getId())
                    .itineraryId(itinerary.getId())
                    .itineraryType(itinerary.getItineraryType())
                    .itineraryName(itinerary.getItineraryName())
                    .build();
        }
    }
}
