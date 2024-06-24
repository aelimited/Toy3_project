package com.travel.toy3.domain.trip.dto;

import com.travel.toy3.domain.trip.entity.Like;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class LikeDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Request {
        @NotNull
        private Long memberId;
        private Long tripId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Response {
        @NotNull
        private Long memberId;
        @NotNull
        private Long tripId;
        private String likeStatus;

        public static Response fromLikeEntity(@NonNull Like like) {
            return Response.builder()
                    .memberId(like.getMember().getId())
                    .tripId(like.getTrip().getId())
                    .likeStatus(like.getStatus())
                    .build();
        }
//        public static likeResponse fromLikeListEntity(
//                Like like,
//                Integer likeCount ){
//            return likeResponse.builder()
//                    .likeStatus(like.getStatus())
//                    .build();
//        }
    }

}
