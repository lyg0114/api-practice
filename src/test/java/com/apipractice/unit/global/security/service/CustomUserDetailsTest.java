package com.apipractice.unit.global.security.service;

import static com.apipractice.global.security.type.RoleType.ADMIN;
import static com.apipractice.global.security.type.RoleType.USER;

import com.apipractice.domain.member.entity.Member;
import com.apipractice.domain.member.entity.Role;
import com.apipractice.global.security.service.CustomUserDetails;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.security.service
 * @since : 24.05.24
 */
class CustomUserDetailsTest {

  @DisplayName("Member.roles이 1개 이상 존재할경우 CustomUserDetails 변환에 성공한다")
  @Test
  void customUserDetailsTest() {
    Member member = Member.builder()
        .roles(List.of(
            Role.builder().roleName(USER.getKey()).build(),
            Role.builder().roleName(ADMIN.getKey()).build()
            ))
        .build();
    CustomUserDetails customUserDetails = new CustomUserDetails(member);
    String authoritiesStr = customUserDetails.getAuthoritiesStr();
    System.out.println("authoritiesStr = " + authoritiesStr);
  }
}