package com.travel.toy3.domain.itinerary.repository;

import com.travel.toy3.domain.itinerary.entity.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {
    Stay findByItinerary_Id(Long itineraryId);
    Stay findStayByItinerary_Id(Long itineraryId);
}
