package com.apipractice.global.security.service;

import com.apipractice.domain.member.entity.Member;
import com.apipractice.domain.member.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.security.service
 * @since : 20.05.24
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member member = memberRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("가입된 이메일이 존재하지 않습니다."));
    return new CustomUserDetails(member);
  }
}
