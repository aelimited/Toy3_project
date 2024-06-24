package com.travel.toy3.domain.member.controller;

import com.travel.toy3.domain.member.dto.MemberDTO;
import com.travel.toy3.domain.member.service.MemberService;
import com.travel.toy3.exception.CustomException;
import com.travel.toy3.util.ApiResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.travel.toy3.exception.CustomErrorCode.*;

@Slf4j
@RestController
@RequestMapping("/api/members")
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<Object>> login(
            @RequestBody MemberDTO memberDTO, HttpSession session
    ) {
        SecurityContextHolder.clearContext(); // 강제 clear
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            throw new CustomException(UNACCEPTABLE_LOGIN_REQUEST);
        }

        UserDetails userDetails = memberService.authenticate(memberDTO);

        // 인증 객체 생성
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authToken);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        log.info("로그인 성공");
        var response = ApiResponse.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data("로그인 성공")
                .build();

        return ResponseEntity.ok().body(response);
    }

    // 로그인 상태 확인
    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Object>> checkLogin(HttpSession session) {
        Authentication authentication = memberService.getAuthentication(session);

        var response = ApiResponse.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data("현재 로그인된 사용자: " + authentication.getName())
                .build();
        return ResponseEntity.ok().body(response);
    }

    // 로그아웃
    @PostMapping("/signout")
    public ResponseEntity<ApiResponse<Object>> logout(HttpSession session) {
        memberService.signOut(session);

        var response = ApiResponse.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data("로그아웃 성공")
                .build();
        return ResponseEntity.ok().body(response);
    }

    // 회원 전체 조회
    @GetMapping("/admin")
    public List<MemberDTO> getAllMembers() {
        return memberService.getAllMembers()
                .stream()
                .map(MemberDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<ApiResponse<Object>> createMember(
            @Valid @RequestBody MemberDTO memberDTO,
            HttpSession session
    ) {
        memberService.createMember(memberDTO, session);

        var response = ApiResponse.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data("회원가입 성공")
                .build();
        return ResponseEntity.ok().body(response);
    }

}
