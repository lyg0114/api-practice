package com.apipractice;

import static com.apipractice.domain.common.Address.createAddress;
import static com.apipractice.global.security.type.RoleType.ADMIN;
import static com.apipractice.global.security.type.RoleType.GUEST;
import static com.apipractice.global.security.type.RoleType.USER;

import com.apipractice.domain.member.application.repository.MemberRepository;
import com.apipractice.domain.member.application.repository.MemberRoleRepository;
import com.apipractice.domain.member.application.repository.RoleRepository;
import com.apipractice.domain.member.entity.Member;
import com.apipractice.domain.member.entity.MemberRole;
import com.apipractice.domain.member.entity.Role;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice
 * @since : 23.05.24
 */
@Configuration
public class ApplicationConfig {

  @Profile({"local"})
  @Bean(name = "initMemberRunner")
  public CommandLineRunner initMember(
      MemberRepository memberRepository,
      MemberRoleRepository memberRoleRepository,
      RoleRepository roleRepository,
      PasswordEncoder encoder
  ) {
    return args -> {
      Member savedUserMember = memberRepository.save(Member.builder()
          .name("박유저")
          .nickname("홍길동")
          .email("user@email.com")
          .password(encoder.encode("test1234"))
          .address(createAddress("서울", "강남", "12345"))
          .build());

      Member savedAdminMember = memberRepository.save(Member.builder()
          .name("김관리")
          .nickname("김과장")
          .email("admin@email.com")
          .password(encoder.encode("test1234"))
          .address(createAddress("서울", "강남", "12345"))
          .build());

      Role saveAdminRole = roleRepository.save(Role.builder().roleName(ADMIN.getKey()).comment(ADMIN.getTitle()).build());
      Role saveUserRole = roleRepository.save(Role.builder().roleName(USER.getKey()).comment(USER.getTitle()).build());
      Role saveGuestRole = roleRepository.save(Role.builder().roleName(GUEST.getKey()).comment(GUEST.getTitle()).build());

      memberRoleRepository.saveAll(List.of(
          memberRoleRepository.save(MemberRole.builder().member(savedAdminMember).role(saveAdminRole).build()),
          memberRoleRepository.save(MemberRole.builder().member(savedUserMember).role(saveUserRole).build()),
          memberRoleRepository.save(MemberRole.builder().member(savedUserMember).role(saveGuestRole).build())
      ));
    };
  }
}
