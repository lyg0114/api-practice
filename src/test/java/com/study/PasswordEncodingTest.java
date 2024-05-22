package com.study;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author : iyeong-gyo
 * @package : com.study
 * @since : 22.05.24
 */
public class PasswordEncodingTest {

  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Test
  void encodingTest(){
    PasswordEncoder encoder = passwordEncoder();
    String encode = encoder.encode("test1234");
    System.out.println("encode = " + encode);
  }

  @Test
  void decodingTest(){
    PasswordEncoder encoder = passwordEncoder();
    String encodedPw = "$2a$10$V1xwB.9uBw0O3UUoIl9CauPsPFm14vLL5/St50dlZ9JHe.k6pI9Wq";
    assertThat(encoder.matches("test1234", encodedPw)).isTrue();
  }

}
