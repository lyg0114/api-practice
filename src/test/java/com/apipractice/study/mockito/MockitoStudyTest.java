package com.apipractice.study.mockito;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import org.junit.jupiter.api.Test;

/**
 * @author : iyeong-gyo
 * @package : com.study
 * @since : 18.05.24
 */
public class MockitoStudyTest {
  @Test
  void mockTest() {
    LinkedList mockedList = mock(LinkedList.class);
    //stubbing
    when(mockedList.get(0)).thenReturn("first");

    //following prints "first"
    System.out.println(mockedList.get(0));

    //following prints "null" because get(999) was not stubbed
    System.out.println(mockedList.get(999));
  }

  @Test
  void mockExceptionTest() {
    LinkedList mockedList = mock(LinkedList.class);
    //stubbing
    when(mockedList.get(1)).thenThrow(new RuntimeException());

    //following throws runtime exception
    assertThatThrownBy(() -> {
      System.out.println(mockedList.get(1));
    }).isInstanceOf(RuntimeException.class);
  }
}
