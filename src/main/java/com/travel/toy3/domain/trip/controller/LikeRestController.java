package com.travel.toy3.domain.trip.controller;

import com.travel.toy3.domain.trip.dto.LikeDTO;
import com.travel.toy3.domain.trip.service.LikeService;
import com.travel.toy3.exception.CustomErrorCode;
import com.travel.toy3.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/like")
public class LikeRestController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/{trip-id}")
    public LikeDTO.Response addLike(
            @PathVariable("trip-id") Long tripId
    ) {
        if(tripId == null) {
            throw new CustomException(CustomErrorCode.INVALID_TRIP);
        }
        return likeService.addLike(tripId);
    }

    @PutMapping("/{trip-id}")
    public LikeDTO.Response updateLike(
            @PathVariable("trip-id") Long tripId
    ) {
        return likeService.updateLike(tripId);
    }
}
