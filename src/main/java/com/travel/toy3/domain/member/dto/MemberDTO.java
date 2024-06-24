package com.travel.toy3.domain.member.dto;

import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.entity.Role;
import com.travel.toy3.domain.trip.entity.Trip;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
//    @Email(message = "잘못된 이메일 형식입니다.")
    private String username;
    private String password;
    private String uname;
    // 본인이 작성한 여행 정보 리스트
    private List<Trip> trips;
    private Set<String> roles;

    public static MemberDTO fromEntity(Member member) {
        return MemberDTO.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .uname(member.getUname())
                .roles(member.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .build();
    }

    public Member toEntity() {
        return new Member(this.username, this.password, this.uname);
    }
}
