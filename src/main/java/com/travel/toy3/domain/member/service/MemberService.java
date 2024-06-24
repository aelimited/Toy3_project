package com.travel.toy3.domain.member.service;

import com.travel.toy3.domain.member.dto.CustomMember;
import com.travel.toy3.domain.member.dto.MemberDTO;
import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.entity.Role;
import com.travel.toy3.domain.member.repository.MemberRepository;
import com.travel.toy3.exception.CustomException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static com.travel.toy3.exception.CustomErrorCode.*;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    public MemberService(MemberRepository memberRepository,
                         RoleService roleService,
                         BCryptPasswordEncoder passwordEncoder,
                         UserDetailsService userDetailsService
    ) {
        this.memberRepository = memberRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member saveMember(Member member) {

        String hashedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(hashedPassword);
        Role userRole = roleService.findByName("USER");
        member.addRole(userRole);

        return memberRepository.save(member);
    }

    public UserDetails authenticate(MemberDTO memberDTO) {
        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(memberDTO.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new CustomException(INVALID_USERNAME);
        }
        if (!passwordEncoder.matches(memberDTO.getPassword(), userDetails.getPassword())) {
            throw new CustomException(INVALID_PASSWORD);
        }
        return userDetails;
    }

    public Authentication getAuthentication(HttpSession session) {
        SecurityContext securityContext = (SecurityContext) session
                .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        if (securityContext == null || securityContext.getAuthentication() == null) {
            throw new CustomException(NO_ACCESS_PERMISSION);
        }
        return securityContext.getAuthentication();
    }

    public void signOut(HttpSession session) {
        SecurityContext securityContext = (SecurityContext) session
                .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (securityContext == null || securityContext.getAuthentication() == null) {
            throw new CustomException(UNACCEPTABLE_LOGOUT_REQUEST);
        }
        log.info("로그아웃 성공");
        session.invalidate();
        SecurityContextHolder.clearContext();
    }

    public void createMember(MemberDTO memberDTO, HttpSession session) {
        SecurityContext securityContext = (SecurityContext) session
                .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        UserDetails existingUser = null;
        if (securityContext != null && securityContext.getAuthentication() != null) {
            throw new CustomException(UNACCEPTABLE_JOIN_REQUEST);
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$"; // 이메일 형식 검사 정규식
        Pattern pattern = Pattern.compile(emailRegex);
        // 아이디가 이메일 형식에 맞지 않으면
        if (!pattern.matcher(memberDTO.getUsername()).matches()) {
            throw new CustomException(INCORRECT_USERNAME_FORMAT);
        }
        try {
            existingUser = userDetailsService.loadUserByUsername(memberDTO.getUsername());
            if (existingUser != null) {
                throw new CustomException(DUPLICATED_USERNAME);
            }
        } catch (UsernameNotFoundException e) {
            // 사용자를 찾지 못하는 경우 회원가입 진행
            Member member = memberDTO.toEntity();
            Member savedMember = saveMember(member);
        }
    }
}
