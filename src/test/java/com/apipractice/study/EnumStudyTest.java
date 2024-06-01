package com.apipractice.study;

import static com.apipractice.global.security.type.RoleType.fromKey;

import com.apipractice.global.security.type.RoleType;
import org.junit.jupiter.api.Test;

/**
 * @author : iyeong-gyo
 * @package : com.study
 * @since : 23.05.24
 */
public class EnumStudyTest {

  @Test
  void enumTest(){
    RoleType roleAdmin = fromKey("ROLE_ADMIN");
    System.out.println("roleAdmin.getKey() = " + roleAdmin.getKey());
    System.out.println("roleAdmin.getTitle() = " + roleAdmin.getTitle());
    System.out.println("roleAdmin.name() = " + roleAdmin.name());
  }


}
