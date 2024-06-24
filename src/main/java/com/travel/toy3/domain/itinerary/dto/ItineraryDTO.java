package com.travel.toy3.domain.itinerary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travel.toy3.domain.itinerary.entity.Accommodation;
import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.entity.Moving;
import com.travel.toy3.domain.itinerary.entity.Stay;
import com.travel.toy3.domain.itinerary.type.ItineraryType;
import com.travel.toy3.util.Geocoding;
import lombok.*;

import java.io.IOException;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItineraryDTO {
    private Long tripId;
    private Long itineraryId;
    private ItineraryType itineraryType;
    private String itineraryName;
    private MovingDTO moving;
    private AccommodationDTO accommodation;
    private StayDTO stay;

    public static ItineraryDTO fromMovingEntity(@NonNull Itinerary itinerary, @NonNull Moving moving) throws IOException {

        return ItineraryDTO.builder()
                .tripId(itinerary.getTrip().getId())
                .itineraryId(itinerary.getId())
                .itineraryType(itinerary.getItineraryType())
                .itineraryName(itinerary.getItineraryName())
                .moving(MovingDTO.builder()
                        .vehicle(moving.getVehicle())
                        .departurePlace(moving.getDeparturePlace())
                        .destinationPlace(moving.getDestinationPlace())
                        .departurePlaceAddress(Geocoding.getAddress(moving.getDeparturePlaceLatitude(), moving.getDeparturePlaceLongitude()))
                        .destinationPlaceAddress(Geocoding.getAddress(moving.getDestinationPlaceLatitude(), moving.getDestinationPlaceLongitude()))
                        .departureDatetime(moving.getDepartureDatetime())
                        .arrivalDatetime(moving.getArrivalDatetime())
                        .build())
                .build();
    }

    public static ItineraryDTO fromAccommodationEntity(@NonNull Itinerary itinerary, @NonNull Accommodation accommodation) throws IOException {

        return ItineraryDTO.builder()
                .tripId(itinerary.getTrip().getId())
                .itineraryId(itinerary.getId())
                .itineraryType(itinerary.getItineraryType())
                .itineraryName(itinerary.getItineraryName())
                .accommodation(AccommodationDTO.builder()
                        .accommodationPlaceName(accommodation.getAccommodationPlaceName())
                        .accommodationPlaceAddress(Geocoding.getAddress(accommodation.getAccommodationPlaceLatitude(), accommodation.getAccommodationPlaceLongitude()))
                        .checkIn(accommodation.getCheckIn())
                        .checkOut(accommodation.getCheckOut())
                        .build())
                .build();
    }

    public static ItineraryDTO fromStayEntity(@NonNull Itinerary itinerary, @NonNull Stay stay) throws IOException {

        return ItineraryDTO.builder()
                .tripId(itinerary.getTrip().getId())
                .itineraryId(itinerary.getId())
                .itineraryType(itinerary.getItineraryType())
                .itineraryName(itinerary.getItineraryName())
                .stay(StayDTO.builder()
                        .stayPlaceName(stay.getStayPlaceName())
                        .stayPlaceAddress(Geocoding.getAddress(stay.getStayPlaceLatitude(), stay.getStayPlaceLongitude()))
                        .arrivalDatetime(stay.getArrivalDatetime())
                        .departureDatetime(stay.getDepartureDatetime())
                        .build())
                .build();
    }
}
