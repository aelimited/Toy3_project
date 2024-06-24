package com.travel.toy3.domain.trip.repository;

import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.trip.entity.Like;
import com.travel.toy3.domain.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findByMemberAndTrip(Member member, Trip trip);
    Long countByTripIdAndStatus(Long tripId, String status);
    List<Like> findByMemberIdAndStatus(Long memberId, String status);
}