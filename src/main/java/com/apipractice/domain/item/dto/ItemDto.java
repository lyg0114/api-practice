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

    private AlbumItemRequest album;
    private BookItemRequest book;
    private MovieItemRequest movie;
  }

  @ToString
  @Getter
  @Builder
  @AllArgsConstructor(access = PRIVATE)
  public static class AlbumItemRequest {

    private String artist;
    private String etc;
  }

  @ToString
  @Getter
  @Builder
  @AllArgsConstructor(access = PRIVATE)
  public static class BookItemRequest {

    private String author;
    private String isbn;
  }

  @ToString
  @Getter
  @Builder
  @AllArgsConstructor(access = PRIVATE)
  public static class MovieItemRequest {

    private String director;
    private String actor;
  }
}
