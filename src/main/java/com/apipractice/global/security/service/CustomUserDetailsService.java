package com.apipractice.global.security.service;

import com.apipractice.domain.member.application.repository.RoleRepository;
import com.apipractice.domain.member.entity.Member;
import com.apipractice.domain.member.application.repository.MemberRepository;
import com.apipractice.domain.member.entity.Role;
import java.util.List;
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
  private final RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member member = memberRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("가입된 이메일이 존재하지 않습니다."));

    List<Role> roles = roleRepository.findRolesByMemberId(member.getId());

    return new CustomUserDetails(member, roles);
  }
}
