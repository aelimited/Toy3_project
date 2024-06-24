package com.travel.toy3.domain.itinerary.dto;

import com.travel.toy3.domain.itinerary.entity.Accommodation;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AccommodationDTO {
    private String accommodationPlaceName;
    private String accommodationPlaceAddress; // 숙소 주소
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}
