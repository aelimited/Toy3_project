package com.travel.toy3.domain.itinerary.repository;

import com.travel.toy3.domain.itinerary.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {
    List<Itinerary> findByTrip_Id(Long tripId);
}
