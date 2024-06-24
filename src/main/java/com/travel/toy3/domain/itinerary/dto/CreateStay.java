package com.travel.toy3.domain.itinerary.dto;

import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.entity.Stay;
import com.travel.toy3.domain.itinerary.type.ItineraryType;
import com.travel.toy3.util.Geocoding;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.IOException;
import java.time.LocalDateTime;

public class CreateStay {
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
        private String stayPlaceName;
        @NotNull
        private String stayPlaceAddress; // 장소 주소
        @NotNull
        private LocalDateTime arrivalDatetime;
        @NotNull
        private LocalDateTime departureDatetime;
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
        private String stayPlaceName;
        private String stayPlaceAddress; // 장소 주소
        private LocalDateTime arrivalDatetime;
        private LocalDateTime departureDatetime;

        public static Response fromEntity(@NonNull Itinerary itinerary, @NonNull Stay stay) throws IOException {
            return Response.builder()
                    .tripId(itinerary.getTrip().getId())
                    .itineraryId(itinerary.getId())
                    .itineraryType(itinerary.getItineraryType())
                    .itineraryName(itinerary.getItineraryName())
                    .stayPlaceName(stay.getStayPlaceName())
                    .stayPlaceAddress(Geocoding.getAddress(stay.getStayPlaceLatitude(), stay.getStayPlaceLongitude()))
                    .arrivalDatetime(stay.getArrivalDatetime())
                    .departureDatetime(stay.getDepartureDatetime())
                    .build();
        }
    }
}
