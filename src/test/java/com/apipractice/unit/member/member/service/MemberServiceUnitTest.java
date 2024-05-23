package com.apipractice.unit.member.member.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.apipractice.domain.member.application.service.MemberService;
import com.apipractice.domain.member.dto.MemberDto.SignUpRequest;
import com.apipractice.domain.member.application.repository.MemberRepository;
import com.apipractice.global.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.unit.member.member.service
 * @since : 18.05.24
 */
@ExtendWith(MockitoExtension.class)
public class MemberServiceUnitTest {

  @InjectMocks MemberService memberService;
  @Mock MemberRepository memberRepository;

  SignUpRequest getSignUpRequest() {
    String email = "test@test.com";
    String password = "test123456!";
    String name = "yglee";
    String nickname = "ygleeee";
    return SignUpRequest.builder()
        .email(email)
        .password(password)
        .name(name)
        .nickname(nickname)
        .build();
  }

  @DisplayName("회원가입을 위한 정보 입력에 성공한다")
  @Test
  void signUp_success_test() {
    //given
    given(memberRepository.existsByEmail(any())).willReturn(false);
    given(memberRepository.existsByNickname(any())).willReturn(false);
    SignUpRequest requestDto = getSignUpRequest();

    //when, then
    memberService.signUp(requestDto);
  }

  @DisplayName("동일한 이메일이 있을경우 회원가입에 실패한다")
  @Test
  void signUp_fail_test_1() {
    //given
    given(memberRepository.existsByEmail(any())).willReturn(true);
    SignUpRequest requestDto = getSignUpRequest();

    //when, then
    assertThatThrownBy(() -> {
      memberService.signUp(requestDto);
    }).isInstanceOf(CustomException.class);
  }

  @DisplayName("동일한 nickname이 있을경우 회원가입에 실패한다")
  @Test
  void signUp_fail_test_2() {
    //given
    given(memberRepository.existsByEmail(any())).willReturn(false);
    given(memberRepository.existsByNickname(any())).willReturn(true);
    SignUpRequest requestDto = getSignUpRequest();

    //when, then
    assertThatThrownBy(() -> {
      memberService.signUp(requestDto);
    }).isInstanceOf(CustomException.class);
  }
}
