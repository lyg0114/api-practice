package com.apipractice.global.security.service;

import static com.apipractice.global.exception.CustomErrorCode.USER_NOT_HAVE_ROLE;
import static java.util.Objects.isNull;

import com.apipractice.domain.member.entity.Member;
import com.apipractice.domain.member.entity.Role;
import com.apipractice.global.exception.CustomException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.global.security.service
 * @since : 20.05.24
 */
@Getter
public class CustomUserDetails implements UserDetails {

  private final Member member;
  private final List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
  private final String roleStrs;

  public CustomUserDetails(Member member, List<Role> roles) {

    this.member = member;

    if (isNull(roles) || roles.isEmpty()) {
      throw new CustomException(USER_NOT_HAVE_ROLE);
    }

    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < roles.size(); i++) {
      this.grantedAuthorities.add(new SimpleGrantedAuthority(roles.get(i).getRoleName()));
      sb.append(roles.get(i).getRoleName());
      if (i < roles.size() - 1) {
        sb.append(",");
      }
    }

    this.roleStrs = sb.toString();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return grantedAuthorities;
  }

  public String getAuthoritiesStr() {
    return roleStrs;
  }

  @Override
  public String getPassword() {
    return member.getPassword();
  }

  @Override
  public String getUsername() {
    return member.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
