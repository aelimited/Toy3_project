package com.travel.toy3.domain.itinerary.dto;

import com.travel.toy3.domain.itinerary.entity.Accommodation;
import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.type.ItineraryType;
import com.travel.toy3.util.Geocoding;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.IOException;
import java.time.LocalDateTime;

public class CreateAccommodation {
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
        private String accommodationPlaceName;
        @NotNull
        private String accommodationPlaceAddress; // 숙소 주소
        @NotNull
        private LocalDateTime checkIn;
        @NotNull
        private LocalDateTime checkOut;
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
        private String accommodationPlaceName;
        private String accommodationPlaceAddress; // 숙소 주소
        private LocalDateTime checkIn;
        private LocalDateTime checkOut;

        public static Response fromEntity(@NonNull Itinerary itinerary, @NonNull Accommodation accommodation) throws IOException {
            return Response.builder()
                    .tripId(itinerary.getTrip().getId())
                    .itineraryId(itinerary.getId())
                    .itineraryType(itinerary.getItineraryType())
                    .itineraryName(itinerary.getItineraryName())
                    .accommodationPlaceName(accommodation.getAccommodationPlaceName())
                    .accommodationPlaceAddress(Geocoding.getAddress(accommodation.getAccommodationPlaceLatitude(), accommodation.getAccommodationPlaceLongitude()))
                    .checkIn(accommodation.getCheckIn())
                    .checkOut(accommodation.getCheckOut())
                    .build();
        }
    }
}
