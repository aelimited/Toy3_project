package com.travel.toy3.domain.member.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사용자 id

    @Email(message = "잘못된 이메일 형식입니다.")
    @Column(unique = true, nullable = false)
    private String username; // 사용자 ID (이메일)

    @Column(nullable = false)
    private String password; // 사용자 비밀번호

    @Column(nullable = false)
    private String uname; // 사용자 이름

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_role",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Member(String username, String password, String uname) {
        this.username = username;
        this.password = password;
        this.uname = uname;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
