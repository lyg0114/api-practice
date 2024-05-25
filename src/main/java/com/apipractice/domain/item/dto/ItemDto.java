package com.apipractice.domain.item.dto;

import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.dto
 * @since : 25.05.24
 *
 *  - 새로운 유형의 Item을 추가하려면 inner 클래스 선언후 ItemRequest 의 필드에 추가한다.
 *  - inner 클래스의 유효성을 검사하려면 @Valid 어노테이션을 붙여야 한다.
 */
public class ItemDto {

  @ToString
  @Getter
  @Builder
  @AllArgsConstructor(access = PRIVATE)
  public static class ItemRequest {

    @NotBlank(message = "물품명을 입력 해주세요.")
    private String name;

    @NotNull(message = "가격을 입력 해주세요.")
    @DecimalMin(value = "0.0", inclusive = false, message = "가격은 0보다 커야 합니다.")
    private BigDecimal price;

    @Min(value = 0, message = "재고는 0보다 커야 합니다.")
    private int stockQuantity;

    @Valid
    private AlbumItemRequest album;
    @Valid
    private BookItemRequest book;
    @Valid
    private MovieItemRequest movie;
  }

  @ToString
  @Getter
  @Builder
  @AllArgsConstructor(access = PRIVATE)
  public static class AlbumItemRequest {

    @NotBlank(message = "아티스트명을 입력 해주세요.")
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
