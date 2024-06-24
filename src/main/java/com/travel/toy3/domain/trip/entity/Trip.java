package com.travel.toy3.domain.trip.entity;

import com.travel.toy3.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 여행 id

    @ManyToOne(fetch = FetchType.EAGER) // 1:N 인 경우에 사용
    @JoinColumn(name = "memberId")
    private Member member; // 사용자 id

    private String tripName; // 여행 이름
    private LocalDate tripDepartureDate; // 여행 출발 날짜
    private LocalDate tripArrivalDate; // 여행 도착 날짜
    private String tripDestination; // 여행지
    private Boolean isDomestic; // 국내외 여부

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

}