package com.apipractice.global.security.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : Hunseong-Park
 * @date : 2022-11-16
 */
@Getter
@RequiredArgsConstructor
public enum RoleType {

  ADMIN("ROLE_ADMIN", "관리자"),
  USER("ROLE_USER", "일반 사용자"),
  SELLER("ROLE_SELLER", "판매자 "),
  GUEST("ROLE_GUEST", "손님");

  private final String key;
  private final String title;

  public static RoleType fromKey(String key) {
    for (RoleType role : RoleType.values()) {
      if (role.getKey().equals(key)) {
        return role;
      }
    }
    throw new IllegalArgumentException("No enum constant with key " + key);
  }
}
