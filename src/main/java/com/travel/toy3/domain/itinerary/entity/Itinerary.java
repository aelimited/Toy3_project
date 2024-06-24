package com.travel.toy3.domain.itinerary.entity;

import com.travel.toy3.domain.itinerary.type.ItineraryType;
import com.travel.toy3.domain.trip.entity.Trip;
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
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 여정 id (PK)

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tripId")
    private Trip trip; // 여행 테이블과 관계 설정 -> 여행 id(FK)

    @Enumerated(EnumType.STRING)
    private ItineraryType itineraryType; // 여정 타입
    private String itineraryName; // 여정 이름

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
