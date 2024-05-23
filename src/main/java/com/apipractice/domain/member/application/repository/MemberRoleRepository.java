package com.apipractice.domain.member.application.repository;

import com.apipractice.domain.member.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.member.entity
 * @since : 18.05.24
 */
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

}
