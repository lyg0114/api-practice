package com.apipractice.study.mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author : iyeong-gyo
 * @package : com.study
 * @since : 18.05.24
 */
@ExtendWith(MockitoExtension.class) // 어노테이션을 사용할 경우 추가해 주어야함
public class MockitoAnnotationStudyTest {

  @InjectMocks Culator culator;   // 테스트 대상 클래스
  @Mock Plus plus;                // 테스트 대상 클래스에서 의존하고 있는 클래스
  @Mock Minus minus;              // 테스트 대상 클래스에서 의존하고 있는 클래스

  // primitive 타입일 경우 타입에 맞는 메서드를 사용해야함
  // 클래스 타입일 경우 any() 사용해도 무방
  // 불필요한 stub이 있을 경우 mockito가 자동으로 exception을 발생시킴
  @Test
  void plusMockTest() {
    given(plus.sum(anyInt(), anyInt())).willReturn(3);
    given(minus.substract(any(), any())).willReturn(2);

    int sum = culator.sum(2, 3);
    int sub = culator.substract(3, 2);

    assertThat(sum).isEqualTo(3);
    assertThat(sub).isEqualTo(2);
  }

  static class Culator {

    private Plus plus;
    private Minus minus;

    public int sum(int x, int y) {
      return plus.sum(x, y);
    }

    public int substract(int x, int y) {
      return minus.substract(x, y);
    }
  }

  static class Plus {

    public int sum(int x, int y) {
      return x + y;
    }
  }

  static class Minus {

    public int substract(Integer x, Integer y) {
      return x - y;
    }
  }
}
