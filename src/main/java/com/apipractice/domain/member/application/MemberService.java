package com.apipractice.domain.member.application;

import static com.apipractice.global.exception.CustomErrorCode.EMAIL_ALREADY_EXIST;
import static com.apipractice.global.exception.CustomErrorCode.NICKNAME_ALREADY_EXIST;

import com.apipractice.domain.member.dto.MemberDto;
import com.apipractice.domain.member.entity.MemberRepository;
import com.apipractice.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.member.application
 * @since : 18.05.24
 */
@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public void signUp(MemberDto.SignUpRequest requestDto) {
    validateDuplicateEmail(requestDto.getEmail());
    validateDuplicateNickName(requestDto.getNickname());
    memberRepository.save(requestDto.toEntity());
  }

  private void validateDuplicateEmail(String email) {
    if (memberRepository.existsByEmail(email)) {
      throw new CustomException(EMAIL_ALREADY_EXIST);
    }
  }

  private void validateDuplicateNickName(String nickname) {
    if (memberRepository.existsByNickname(nickname)) {
      throw new CustomException(NICKNAME_ALREADY_EXIST);
    }
  }
}
