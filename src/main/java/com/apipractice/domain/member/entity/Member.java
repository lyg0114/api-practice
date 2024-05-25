package com.apipractice.domain.member.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.apipractice.domain.common.Address;
import com.apipractice.domain.common.BaseTimeEntity;
import com.apipractice.domain.order.entity.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.member.entity
 * @since : 18.05.24
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "nickname", nullable = false)
  private String nickname;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "refresh_token")
  private String refreshToken;

  @Embedded
  private Address address;

  @OneToMany(mappedBy = "member")
  private List<Order> orders;

  @OneToMany(mappedBy = "member")
  private List<MemberRole> memberRoles;

  public void updateRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public void deleteRefreshToken() {
    this.refreshToken = null;
  }
}
