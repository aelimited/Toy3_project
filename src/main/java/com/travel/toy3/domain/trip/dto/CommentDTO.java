package com.travel.toy3.domain.trip.dto;

import com.travel.toy3.domain.trip.entity.Comment;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

public class CommentDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request {
        @NotNull
        private String content;
        @NotNull
        private Long tripId;
        @NotNull
        private Long memberId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UpdateRequest {
        @NotNull
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String content;
        private Long tripId;
        private Long memberId;

        public static Response fromEntity(@NonNull Comment comment) {
            return Response.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .tripId(comment.getTrip().getId())
                    .memberId(comment.getMember().getId())
                    .build();
        }
    }
}
