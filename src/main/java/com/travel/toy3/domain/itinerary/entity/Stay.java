package com.travel.toy3.domain.itinerary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Stay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 체류 id (PK)

    @ManyToOne
    @JoinColumn(name = "itineraryId")
    private Itinerary itinerary; // 여정 테이블과 관계 설정 -> 여정 id (FK)

    private String stayPlaceName; // 장소명
    private Double stayPlaceLatitude; // 장소 위도
    private Double stayPlaceLongitude; // 장소 경도
    private LocalDateTime arrivalDatetime; // 도착 일시
    private LocalDateTime departureDatetime; // 출발 일시

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
