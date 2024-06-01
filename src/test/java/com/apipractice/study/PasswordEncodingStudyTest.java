package com.apipractice.study;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : iyeong-gyo
 * @package : com.study
 * @since : 22.05.24
 */
public class PasswordEncodingStudyTest {

  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @DisplayName("패스워드 인코딩 테스트")
  @Test
  void encodingTest() {
    PasswordEncoder encoder = passwordEncoder();
    String encode = encoder.encode("test1234");
    System.out.println("encode = " + encode);
  }

  @DisplayName("패스워드 검증 테스트")
  @Test
  void decodingTest() {
    PasswordEncoder encoder = passwordEncoder();
    String encodedPw = "$2a$10$V1xwB.9uBw0O3UUoIl9CauPsPFm14vLL5/St50dlZ9JHe.k6pI9Wq";
    assertThat(encoder.matches("test1234", encodedPw)).isTrue();
  }

}
