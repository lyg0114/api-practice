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

    ADMIN("ROLE_ADMIN", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

}
