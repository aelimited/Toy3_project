package com.travel.toy3.domain.trip.repository;

import com.travel.toy3.domain.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<List<Trip>> findByTripDestination(String destination);
    Optional<Trip> findById(Long tripId);
    List<Trip> findByMemberId(Long memberId);
}