package com.apipractice.unit.global.security.service;

import static com.apipractice.global.security.type.RoleType.ADMIN;
import static com.apipractice.global.security.type.RoleType.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.apipractice.domain.member.entity.Member;
import com.apipractice.domain.member.entity.Role;
import com.apipractice.global.exception.CustomException;
import com.apipractice.global.security.service.CustomUserDetails;
import java.util.ArrayList;
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
  void create_custom_user_details_Test() {
    //given
    Member member = Member.builder().build();
    List<Role> roles = List.of(
        Role.builder().roleName(USER.getKey()).build(),
        Role.builder().roleName(ADMIN.getKey()).build()
    );

    //when
    CustomUserDetails customUserDetails = new CustomUserDetails(member, roles);
    String authoritiesStr = customUserDetails.getAuthoritiesStr();

    //then
    assertThat(authoritiesStr).isEqualTo("ROLE_USER,ROLE_ADMIN");
  }

  @DisplayName("Member.roles이 존재하지 않을경우 CustomException 이 발생한다.")
  @Test
  void create_custom_user_details_if_role_no_exist_Test() {
    //given, when
    Member member = Member.builder().build();

    //then
    assertThatThrownBy(() -> {
      new CustomUserDetails(member, new ArrayList<>());
    }).isInstanceOf(CustomException.class);
  }
}