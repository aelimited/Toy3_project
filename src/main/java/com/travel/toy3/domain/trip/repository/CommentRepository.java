package com.travel.toy3.domain.trip.repository;

import com.travel.toy3.domain.trip.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTripId(Long tripId);
    Long countByTripId(Long tripId);
}