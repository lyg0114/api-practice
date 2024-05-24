package com.apipractice.domain.member.application.repository;

import com.apipractice.domain.member.entity.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.member.entity
 * @since : 18.05.24
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

  @Query(
      "select r from Role r "
          + "join fetch r.memberRoles mr "
          + "where mr.member.id = :memberId"
  )
  List<Role> findRolesByMemberId(Long memberId);
}
