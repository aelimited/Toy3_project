package com.travel.toy3.domain.trip.service;

import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.repository.ItineraryRepository;
import com.travel.toy3.domain.member.dto.CustomMember;
import com.travel.toy3.domain.trip.dto.CreateUpdateTrip;
import com.travel.toy3.domain.trip.dto.LikeDTO;
import com.travel.toy3.domain.trip.dto.TripDTO;
import com.travel.toy3.domain.trip.dto.TripDetailDTO;
import com.travel.toy3.domain.trip.entity.Comment;
import com.travel.toy3.domain.trip.entity.Like;
import com.travel.toy3.domain.trip.entity.Trip;
import com.travel.toy3.domain.trip.repository.CommentRepository;
import com.travel.toy3.domain.trip.repository.LikeRepository;
import com.travel.toy3.domain.trip.repository.TripRepository;
import com.travel.toy3.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.travel.toy3.exception.CustomErrorCode.INVALID_TRIP;
import static com.travel.toy3.exception.CustomErrorCode.NO_EDIT_PERMISSION;

@Slf4j
@RequiredArgsConstructor
@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Transactional
    public CreateUpdateTrip.Response addTrip(
            CreateUpdateTrip.Request request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomMember customMember = (CustomMember) authentication.getPrincipal();

        Trip trip = Trip.builder()
                .member(customMember.getMember())
                .tripName(request.getTripName())
                .tripDepartureDate(request.getTripDepartureDate())
                .tripArrivalDate(request.getTripArrivalDate())
                .tripDestination(request.getTripDestination())
                .isDomestic(request.getIsDomestic())
                .build();
        tripRepository.save(trip);

        return CreateUpdateTrip.Response.fromEntity(trip);
    }

    public Trip getByTripId(Long tripId) {
        Optional<Trip> optional = tripRepository.findById(tripId);

        if (optional.isPresent()) {
            Trip trip = optional.get();
            return trip;
        } else
            throw new RuntimeException();
    }

    @Transactional
    public List<TripDTO> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();
        return trips.stream().map(trip -> {
            Integer likeCount = likeRepository.countByTripIdAndStatus(trip.getId(), "Y").intValue();
            Integer commentCount = commentRepository.countByTripId(trip.getId()).intValue();
            return TripDTO.fromEntity(trip, likeCount, commentCount);
        }).collect(Collectors.toList());
    }


    @Transactional
    public TripDetailDTO getTripDetail(Long tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        if (optionalTrip.isEmpty()) {
            throw new CustomException(INVALID_TRIP);
        }
        Trip trip = optionalTrip.get();
        List<Itinerary> itineraries = itineraryRepository.findByTrip_Id(tripId);
        List<Comment> comments = commentRepository.findByTripId(tripId);
        Integer commentCount = commentRepository.countByTripId(trip.getId()).intValue();
        Integer likeCount = likeRepository.countByTripIdAndStatus(trip.getId(), "Y").intValue();

        return TripDetailDTO.fromEntity(trip, itineraries, likeCount, comments, commentCount);
    }

    @Transactional
    public List<CreateUpdateTrip.Response> getTripDestination(String destination) {
        List<Trip> trips = getByDestination(destination);
        List<CreateUpdateTrip.Response> tripList = new ArrayList<>();

        for (Trip trip : trips) {
            CreateUpdateTrip.Response tripResponse = CreateUpdateTrip.Response.fromEntity(trip);
            Long likeCount = likeRepository.countByTripIdAndStatus(trip.getId(), "Y");

            tripResponse.setLikeCount(likeCount);
            tripList.add(tripResponse);
        }
        return tripList;
    }

    private List<Trip> getByDestination(String destination) {
        Optional<List<Trip>> optional = tripRepository.findByTripDestination(destination);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new CustomException(INVALID_TRIP);
        }
    }

    @Transactional
    public CreateUpdateTrip.Response updateTrip(
            Long tripId,
            CreateUpdateTrip.Request request
    ) {
        Trip existingTrip = getByTripId(tripId);

        updateTripFromRequest(existingTrip, request);
        return CreateUpdateTrip.Response.fromEntity(existingTrip);
    }

    private void updateTripFromRequest(Trip trip, CreateUpdateTrip.Request request) {
        trip.setTripName(request.getTripName());
        trip.setTripDepartureDate(request.getTripDepartureDate());
        trip.setTripArrivalDate(request.getTripArrivalDate());
        trip.setTripDestination(request.getTripDestination());
        trip.setIsDomestic(request.getIsDomestic());
    }

    @Transactional
    public List<TripDTO> getOwnTrips() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = ((CustomMember) authentication.getPrincipal()).getMember().getId();

        List<Trip> trips = tripRepository.findByMemberId(memberId);

        return trips.stream().map(trip -> {
            Integer likeCount = likeRepository.countByTripIdAndStatus(trip.getId(), "Y").intValue();
            Integer commentCount = commentRepository.countByTripId(trip.getId()).intValue();
            return TripDTO.fromEntity(trip, likeCount, commentCount);
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<TripDTO> getLikeTrips() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long memberId = ((CustomMember) authentication.getPrincipal()).getMember().getId();

        List<Like> likes = likeRepository.findByMemberIdAndStatus(memberId, "Y");
        List<Trip> likeTrips = likes.stream().map(Like::getTrip).toList();

        return likeTrips.stream().map(trip -> {
            Integer likeCount = likeRepository.countByTripIdAndStatus(trip.getId(), "Y").intValue();
            Integer commentCount = commentRepository.countByTripId(trip.getId()).intValue();
            return TripDTO.fromEntity(trip, likeCount, commentCount);
        }).toList();
    }
}
