package com.travel.toy3.domain.trip.service;

import com.travel.toy3.domain.member.dto.CustomMember;
import com.travel.toy3.domain.trip.dto.LikeDTO;
import com.travel.toy3.domain.trip.entity.Like;
import com.travel.toy3.domain.trip.entity.Trip;
import com.travel.toy3.domain.trip.repository.LikeRepository;
import com.travel.toy3.domain.trip.repository.TripRepository;
import com.travel.toy3.exception.CustomErrorCode;
import com.travel.toy3.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private TripService tripService;

    @Transactional
    public LikeDTO.Response addLike(
            Long tripId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomMember customMember = (CustomMember) authentication.getPrincipal();

        Trip trip = tripRepository.getById(tripId);
        Like existingLike = likeRepository.findByMemberAndTrip(customMember.getMember(), trip);

        if(existingLike == null){

            Like like = Like.builder()
                    .member(customMember.getMember())
                    .trip(trip)
                    .status("Y")
                    .build();
            likeRepository.save(like);
            return LikeDTO.Response.fromLikeEntity(like);
        }else {
            existingLike.setStatus(existingLike.getStatus().equals("Y") ? "N" : "Y");
            likeRepository.save(existingLike);
            return LikeDTO.Response.fromLikeEntity(existingLike);
        }
    }

    @Transactional
    public LikeDTO.Response updateLike(
            Long tripId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomMember customMember = (CustomMember) authentication.getPrincipal();

        Trip existingTrip = tripService.getByTripId(tripId);
        Like existingLike = likeRepository.findByMemberAndTrip(customMember.getMember(), existingTrip);

        if (existingLike == null) {
            throw new CustomException(CustomErrorCode.EMPTY_LIKE_LIST);
        }
        existingLike.setStatus(existingLike.getStatus().equals("Y") ? "N" : "Y");
        Like updatedLike = likeRepository.save(existingLike);

        return LikeDTO.Response.fromLikeEntity(updatedLike);
    }
}