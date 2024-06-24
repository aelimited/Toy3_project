package com.travel.toy3.domain.trip.controller;

import com.travel.toy3.domain.trip.dto.CreateUpdateTrip;
import com.travel.toy3.domain.trip.dto.TripDTO;
import com.travel.toy3.domain.trip.dto.TripDetailDTO;
import com.travel.toy3.domain.trip.service.TripService;
import com.travel.toy3.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/trips")
public class TripRestController {

    @Autowired
    private TripService tripService;

    @PostMapping
    public CreateUpdateTrip.Response addTrip(
            @RequestBody final CreateUpdateTrip.Request request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return tripService.addTrip(request);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TripDTO>>> getTrips() {
        List<TripDTO> trips = tripService.getAllTrips();
        log.info("===== 여행 전체 조회 ======");
        ApiResponse<List<TripDTO>> response = ApiResponse.<List<TripDTO>>builder()
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .resultCode(HttpStatus.OK.value())
                .data(trips)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{trip-id}")
    public ResponseEntity<ApiResponse<TripDetailDTO>> getTripDetail(
            @PathVariable("trip-id") Long tripId
    ) {
        log.info("===== 여행 상세 조회 ======");
        TripDetailDTO responseDto = tripService.getTripDetail(tripId);

        ApiResponse<TripDetailDTO> response = ApiResponse.<TripDetailDTO>builder()
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .resultCode(HttpStatus.OK.value())
                .data(responseDto)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/destination/{trip-destination}")
    public List<CreateUpdateTrip.Response> getDestinationTrip(
            @PathVariable("trip-destination") final String destination
    ) {
        log.info("===== 특정 여행지로 조회 ======");
        return tripService.getTripDestination(destination);
    }

    @PutMapping("/{trip-id}")
    public CreateUpdateTrip.Response updateTrip(
            @PathVariable("trip-id") Long tripId,
            @RequestBody final CreateUpdateTrip.Request request
    ) {
        log.info(" ===== 여행 수정 ===== ");
        return tripService.updateTrip(tripId, request);
    }


    @GetMapping("/like")
    public ResponseEntity<ApiResponse<List<TripDTO>>> getLikeTrips() {
        List<TripDTO> trips = tripService.getLikeTrips();
        ApiResponse<List<TripDTO>> response = ApiResponse.<List<TripDTO>>builder()
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .resultCode(HttpStatus.OK.value())
                .data(trips)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/own")
    public ResponseEntity<ApiResponse<List<TripDTO>>> getOwnTrips() {
        List<TripDTO> trips = tripService.getOwnTrips();
        log.info("===== 여행 전체 조회 ======");
        ApiResponse<List<TripDTO>> response = ApiResponse.<List<TripDTO>>builder()
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .resultCode(HttpStatus.OK.value())
                .data(trips)
                .build();
        return ResponseEntity.ok(response);
    }
}
