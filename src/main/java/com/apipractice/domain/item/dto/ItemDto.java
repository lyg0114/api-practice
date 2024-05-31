package com.apipractice.domain.item.dto;

import static com.apipractice.domain.item.dto.ItemType.ALBUM;
import static com.apipractice.domain.item.dto.ItemType.BOOK;
import static com.apipractice.domain.item.dto.ItemType.MOVIE;
import static com.apipractice.global.exception.CustomErrorCode.ITEM_TYPE_NOT_EXIST;
import static lombok.AccessLevel.PRIVATE;

import com.apipractice.domain.item.entity.Item;
import com.apipractice.domain.item.entity.detail.Album;
import com.apipractice.domain.item.entity.detail.Book;
import com.apipractice.domain.item.entity.detail.Movie;
import com.apipractice.domain.member.entity.Member;
import com.apipractice.global.exception.CustomException;
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
 * <p>
 * - 새로운 유형의 Item을 추가하려면 inner 클래스 선언후 ItemRequest 의 필드에 추가한다. - inner 클래스의 유효성을 검사하려면 @Valid 어노테이션을
 * 붙여야 한다.
 */
public class ItemDto {

  @ToString
  @Getter
  @Builder
  @AllArgsConstructor(access = PRIVATE)
  public static class ItemCondition {

    private String name;
    private BigDecimal loePrice;
    private BigDecimal goePrice;
    private Integer stockQuantity;

    // Album
    private String artist;

    // Book
    private String author;
    private String isbn;

    // Movie
    private String director;
    private String actor;
  }


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

    @NotBlank(message = "물품 종류를 선택해 주세요.")
    private String itemType;

    @Valid
    private AlbumItemRequest album;
    @Valid
    private BookItemRequest book;
    @Valid
    private MovieItemRequest movie;

    public Item toEntity(Member seller) {
      Item.ItemBuilder builder = Item.builder()
          .name(this.name)
          .price(this.price)
          .stockQuantity(this.stockQuantity)
          .itemType(this.itemType)
          .seller(seller);

      if (itemType.equals(ALBUM.getKey())) {
        return builder
            .album(Album.builder()
                .artist(this.album.getArtist())
                .etc(this.album.getEtc())
                .build())
            .build();
      }

      if (itemType.equals(BOOK.getKey())) {
        return builder
            .book(Book.builder()
                .author(this.book.getAuthor())
                .isbn(this.book.getIsbn())
                .build())
            .build();
      }

      if (itemType.equals(MOVIE.getKey())) {
        return builder
            .movie(Movie.builder()
                .director(this.movie.getDirector())
                .actor(this.movie.getActor())
                .build())
            .build();
      }

      throw new CustomException(ITEM_TYPE_NOT_EXIST);
    }
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

  @ToString
  @Getter
  @Builder
  @AllArgsConstructor
  public static class ItemResponse {

    private String name;
    private BigDecimal price;
    private int stockQuantity;
    private String itemType;
    private String sellerEamil;
    private String sellerName;
    private AlbumItemResponse album;
    private BookItemResponse book;
    private MovieItemResponse movie;

    public static ItemResponse fromEntity(Item item) {

      ItemResponse.ItemResponseBuilder builder = ItemResponse.builder();
      builder.name(item.getName());
      builder.price(item.getPrice());
      builder.stockQuantity(item.getStockQuantity());
      builder.itemType(item.getItemType());
      builder.sellerName(item.getSeller().getName());
      builder.sellerEamil(item.getSeller().getEmail());

      if (item.getItemType().equals(ALBUM.getKey())) {
        builder.album(AlbumItemResponse.builder()
            .artist(item.getAlbum().getArtist())
            .etc(item.getAlbum().getEtc())
            .build());
      }

      if (item.getItemType().equals(BOOK.getKey())) {
        builder.book(BookItemResponse.builder()
            .isbn(item.getBook().getIsbn())
            .author(item.getBook().getAuthor())
            .build());
      }

      if (item.getItemType().equals(MOVIE.getKey())) {
        builder.movie(MovieItemResponse.builder()
            .actor(item.getMovie().getActor())
            .director(item.getMovie().getDirector())
            .build());
      }

      return builder.build();
    }
  }

  @ToString
  @Getter
  @Builder
  @AllArgsConstructor(access = PRIVATE)
  public static class AlbumItemResponse {

    private String artist;
    private String etc;
  }

  @ToString
  @Getter
  @Builder
  @AllArgsConstructor(access = PRIVATE)
  public static class BookItemResponse {

    private String author;
    private String isbn;
  }

  @ToString
  @Getter
  @Builder
  @AllArgsConstructor(access = PRIVATE)
  public static class MovieItemResponse {

    private String director;
    private String actor;
  }
}
