package com.travel.toy3.domain.trip.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travel.toy3.domain.itinerary.dto.CreateItinerary;
import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.trip.entity.Comment;
import com.travel.toy3.domain.trip.entity.Trip;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TripDetailDTO {

    private Long id;
    private String username;
    private String tripName;
    private LocalDate tripDepartureDate;
    private LocalDate tripArrivalDate;
    private String tripDestination;
    private Boolean isDomestic;
    private List<CreateItinerary.Response> itineraries;
    private Integer likeCount;
    private Integer commentCount;
    private List<CommentDTO.Response> comments;

    public static TripDetailDTO fromEntity(
            @NotNull Trip trip,
            @NotNull List<Itinerary> itineraries,
            @NotNull Integer likeCount,
            @NotNull List<Comment> comments,
            @NotNull Integer commentCount
    ) {
        List<CreateItinerary.Response> itineraryList = itineraries.stream()
                .map(CreateItinerary.Response::fromEntity)
                .collect(Collectors.toList());


        List<CommentDTO.Response> commentList = comments.stream()
                .map(CommentDTO.Response::fromEntity)
                .collect(Collectors.toList());

        return TripDetailDTO.builder()
                .id(trip.getId())
                .username(trip.getMember().getUsername())
                .tripName(trip.getTripName())
                .tripDepartureDate(trip.getTripDepartureDate())
                .tripArrivalDate(trip.getTripArrivalDate())
                .tripDestination(trip.getTripDestination())
                .isDomestic(trip.getIsDomestic())
                .itineraries(itineraryList)
                .likeCount(likeCount)
                .commentCount(commentCount)
                .comments(commentList)
                .build();
    }
}
