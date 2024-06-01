package com.apipractice.domain.item.entity;

import static com.apipractice.domain.item.dto.ItemType.ALBUM;
import static com.apipractice.domain.item.dto.ItemType.BOOK;
import static com.apipractice.domain.item.dto.ItemType.MOVIE;
import static com.apipractice.global.exception.CustomErrorCode.ITEMTYPE_CANNOT_CHANGE;
import static com.apipractice.global.exception.CustomErrorCode.ITEM_SELLER_NOT_MATCH;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.apipractice.domain.common.BaseTimeEntity;
import com.apipractice.domain.item.dto.ItemDto.AlbumItemResponse;
import com.apipractice.domain.item.dto.ItemDto.AlbumItemResponse.AlbumItemResponseBuilder;
import com.apipractice.domain.item.dto.ItemDto.BookItemResponse;
import com.apipractice.domain.item.dto.ItemDto.ItemRequest;
import com.apipractice.domain.item.dto.ItemDto.ItemResponse;
import com.apipractice.domain.item.dto.ItemDto.ItemResponse.ItemResponseBuilder;
import com.apipractice.domain.item.dto.ItemDto.MovieItemResponse;
import com.apipractice.domain.item.dto.ItemDto.MovieItemResponse.MovieItemResponseBuilder;
import com.apipractice.domain.item.entity.detail.Album;
import com.apipractice.domain.item.entity.detail.Book;
import com.apipractice.domain.item.entity.detail.Movie;
import com.apipractice.domain.member.entity.Member;
import com.apipractice.global.exception.CustomException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.entity
 * @since : 18.05.24
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "item")
@Entity
public class Item extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "item_id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "stock_quantity")
  private int stockQuantity;

  @OneToMany(mappedBy = "item")
  private List<ItemCategory> itemCategorys;

  @Column(name = "item_type")
  private String itemType;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member seller;

  @OneToOne(fetch = LAZY, cascade = ALL) // 영속선 전이 ALL 설정
  @JoinColumn(name = "album_id")
  private Album album;

  @OneToOne(fetch = LAZY, cascade = ALL) // 영속선 전이 ALL 설정
  @JoinColumn(name = "movie_id")
  private Movie movie;

  @OneToOne(fetch = LAZY, cascade = ALL) // 영속선 전이 ALL 설정
  @JoinColumn(name = "book_id")
  private Book book;

  public void updateItem(ItemRequest itemRequest, String email) {
    if (!this.seller.getEmail().equals(email)) {
      throw new CustomException(ITEM_SELLER_NOT_MATCH);
    }

    if (!itemRequest.getItemType().equals(this.itemType)) {
      throw new CustomException(ITEMTYPE_CANNOT_CHANGE);
    }

    this.name = itemRequest.getName();
    this.price = itemRequest.getPrice();
    this.stockQuantity = itemRequest.getStockQuantity();

    if (itemType.equals(ALBUM.getKey())) {
      Album album = this.getAlbum();
      album.updateAlbum(itemRequest.getAlbum());
    }

    if (itemType.equals(BOOK.getKey())) {
      Book book = this.getBook();
      book.updateBook(itemRequest.getBook());
    }

    if (itemType.equals(MOVIE.getKey())) {
      Movie movie = this.getMovie();
      movie.updateMovie(itemRequest.getMovie());
    }
  }

  public ItemResponse toDto() {

    ItemResponseBuilder builder = ItemResponse.builder();
    builder.name(this.name);
    builder.price(this.price);
    builder.stockQuantity(this.stockQuantity);
    builder.itemType(this.itemType);
    builder.sellerEamil(this.seller.getEmail());
    builder.sellerName(this.seller.getName());

    if (itemType.equals(ALBUM.getKey())) {
      AlbumItemResponseBuilder albumBuilder
          = AlbumItemResponse.builder();
      builder.album(albumBuilder
          .artist(this.album.getArtist())
          .etc(this.album.getEtc())
          .build());
    }

    if (itemType.equals(BOOK.getKey())) {
      BookItemResponse.BookItemResponseBuilder bookBuilder
          = BookItemResponse.builder();
      builder.book(bookBuilder
          .author(this.book.getAuthor())
          .isbn(this.book.getIsbn())
          .build());
    }

    if (itemType.equals(MOVIE.getKey())) {
      MovieItemResponseBuilder movieBuilder
          = MovieItemResponse.builder();
      builder.movie(movieBuilder
          .director(this.movie.getDirector())
          .actor(this.movie.getActor())
          .build());
    }

    return builder.build();
  }


  public void updateAlbum(Album album) {
    this.album = album;
  }

  public void updateMovie(Movie movie) {
    this.movie = movie;
  }

  public void updateBook(Book book) {
    this.book = book;
  }
}
