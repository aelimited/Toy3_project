package com.travel.toy3.domain.itinerary.repository;

import com.travel.toy3.domain.itinerary.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    Accommodation findByItinerary_Id(Long itineraryId);
    Accommodation findAccommodationByItinerary_Id(Long itineraryId);
}
