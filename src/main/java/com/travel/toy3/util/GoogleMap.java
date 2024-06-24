package com.travel.toy3.util;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GoogleMap {
    private Double latitude; // 위도
    private Double longitude; // 경도
    private String formattedAddress; // 주소지
}
