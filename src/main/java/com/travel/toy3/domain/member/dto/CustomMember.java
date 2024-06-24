package com.travel.toy3.domain.member.dto;

import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomMember extends User {
    private Member member;
    public CustomMember(Member member) {
        super(member.getUsername(), member.getPassword(), getAuthorities (member.getRoles()));
        this.member=member;
    }
    private static Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList ()); // [ROLE_USER, ROLE_MANAGER, ROLE_ADMIN]
    }
    public void setMember(Member member) {
        this.member = member;
    }
    public Member getMember() {
        return member;
    }
}
