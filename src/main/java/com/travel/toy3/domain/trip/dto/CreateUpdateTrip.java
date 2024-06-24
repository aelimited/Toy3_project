package com.travel.toy3.domain.trip.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travel.toy3.domain.trip.entity.Trip;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

public class CreateUpdateTrip {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class Request {
        @NotNull
        private String tripName;
        @NotNull
        private LocalDate tripDepartureDate;
        @NotNull
        private LocalDate tripArrivalDate;
        @NotNull
        private String tripDestination;
        @NotNull
        private Boolean isDomestic;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Response {
        private Long id;
        private Long memberId;
        private String tripName;
        private LocalDate tripDepartureDate;
        private LocalDate tripArrivalDate;
        private String tripDestination;
        private Boolean isDomestic;
        private Long likeCount;

        public static Response fromEntity(
                @NotNull Trip trip
        ) {
            return Response.builder()
                    .id(trip.getId())
                    .tripName(trip.getTripName())
                    .tripDepartureDate(trip.getTripDepartureDate())
                    .tripArrivalDate(trip.getTripArrivalDate())
                    .tripDestination(trip.getTripDestination())
                    .isDomestic(trip.getIsDomestic())
                    .build();
        }

        public static Response fromLikeEntity(
                @NotNull Trip trip,
                @NotNull Long likeCount
        ) {
            return Response.builder()
                    .id(trip.getId())
                    .tripName(trip.getTripName())
                    .tripDepartureDate(trip.getTripDepartureDate())
                    .tripArrivalDate(trip.getTripArrivalDate())
                    .tripDestination(trip.getTripDestination())
                    .isDomestic(trip.getIsDomestic())
                    .likeCount(likeCount)
                    .build();
        }
    }
}
