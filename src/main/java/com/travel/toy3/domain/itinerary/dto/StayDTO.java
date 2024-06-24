package com.travel.toy3.domain.itinerary.dto;

import com.travel.toy3.domain.itinerary.entity.Stay;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StayDTO {
    private String stayPlaceName;
    private String stayPlaceAddress; // 장소 주소
    private LocalDateTime arrivalDatetime;
    private LocalDateTime departureDatetime;
}
