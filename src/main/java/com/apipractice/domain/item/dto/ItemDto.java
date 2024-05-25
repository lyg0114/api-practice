package com.apipractice.domain.item.dto;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.dto
 * @since : 25.05.24
 */
public class ItemDto {

  @ToString
  @Getter
  @Builder
  @AllArgsConstructor(access = PRIVATE)
  public static class ItemRequest {

    private String name;
    private BigDecimal price;
    private int stockQuantity;

    //Album
    private String artist;
    private String etc;

  }

//  @Getter
//  @Builder
//  public static class AlbumItemRequest extends ItemRequest {
//    private String artist;
//    private String etc;
//  }
//
//  @Getter
//  @Builder
//  public static class BookItemRequest extends ItemRequest {
//    private String author;
//    private String isbn;
//
//  }
//
//  @Getter
//  @Builder
//  public static class MovieItemRequest extends ItemRequest {
//    private String director;
//    private String actor;
//  }

}
