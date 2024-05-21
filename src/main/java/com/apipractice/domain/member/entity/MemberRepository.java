package com.apipractice.domain.member.entity;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.member.entity
 * @since : 18.05.24
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

  boolean existsByEmail(String email);

  Boolean existsByNickname(String nickname);

  Optional<Member> findByEmail(String username);
}
