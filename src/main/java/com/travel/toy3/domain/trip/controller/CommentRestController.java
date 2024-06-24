package com.travel.toy3.domain.trip.controller;

import com.travel.toy3.domain.member.dto.CustomMember;
import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.trip.dto.CommentDTO;
import com.travel.toy3.domain.trip.service.CommentService;
import com.travel.toy3.exception.CustomException;
import com.travel.toy3.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.travel.toy3.exception.CustomErrorCode.NO_ACCESS_PERMISSION;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {
    private final CommentService commentService;

    @Autowired
    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{trip-id}")
    public ResponseEntity<ApiResponse<CommentDTO.Response>> createComment(
            @PathVariable("trip-id") Long tripId,
            @RequestBody CommentDTO.Request requestDto
    ) {
        Member member = getLoggedInMember();
        requestDto.setMemberId(member.getId());
        requestDto.setTripId(tripId);
        CommentDTO.Response responseDto = commentService.createComment(requestDto);

        ApiResponse<CommentDTO.Response> response = ApiResponse.<CommentDTO.Response>builder()
                .resultMessage("댓글 작성 완료")
                .resultCode(HttpStatus.OK.value())
                .data(responseDto)
                .build();
        return ResponseEntity.ok(response);
    }

    // 로그인 상태 확인
    private Member getLoggedInMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomException(NO_ACCESS_PERMISSION);
        }

        CustomMember customMember = (CustomMember) authentication.getPrincipal();
        return customMember.getMember();
    }
    @PutMapping("/{comment-id}")
    public ResponseEntity<ApiResponse<CommentDTO.Response>> updateComment(
            @PathVariable("comment-id") Long commentId,
            @RequestBody CommentDTO.UpdateRequest requestDto
    ) {
        CommentDTO.Response responseDto = commentService.updateComment(commentId, requestDto);
        ApiResponse<CommentDTO.Response> response = ApiResponse.<CommentDTO.Response>builder()
                .resultMessage("댓글 수정 성공")
                .resultCode(HttpStatus.OK.value())
                .data(responseDto)
                .build();
        return ResponseEntity.ok(response);
    }
}
