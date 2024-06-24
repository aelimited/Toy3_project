package com.travel.toy3.domain.member.repository;

import com.travel.toy3.domain.member.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name); // Role name으로 조회
}
