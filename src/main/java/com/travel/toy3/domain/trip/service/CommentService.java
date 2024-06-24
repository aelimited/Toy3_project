package com.travel.toy3.domain.trip.service;

import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.repository.MemberRepository;
import com.travel.toy3.domain.trip.dto.CommentDTO;
import com.travel.toy3.domain.trip.entity.Comment;
import com.travel.toy3.domain.trip.entity.Trip;
import com.travel.toy3.domain.trip.repository.CommentRepository;
import com.travel.toy3.domain.trip.repository.TripRepository;
import com.travel.toy3.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.travel.toy3.exception.CustomErrorCode.*;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final TripRepository tripRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository, TripRepository tripRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.tripRepository = tripRepository;
    }

    public CommentDTO.Response createComment(CommentDTO.Request requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new CustomException(INVALID_USERNAME));

        Trip trip = tripRepository.findById(requestDto.getTripId())
                .orElseThrow(() -> new CustomException(INVALID_TRIP));

        Comment comment = new Comment();
        comment.setContent(requestDto.getContent());
        comment.setMember(member);
        comment.setTrip(trip);

        Comment savedComment = commentRepository.save(comment);

        return CommentDTO.Response.fromEntity(savedComment);
    }

    public CommentDTO.Response updateComment(Long commentId, CommentDTO.UpdateRequest requestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(INVALID_COMMENT_SEARCH_RESULT));

        comment.setContent(requestDto.getContent());

        Comment updatedComment = commentRepository.save(comment);

        return CommentDTO.Response.fromEntity(updatedComment);
    }
}
