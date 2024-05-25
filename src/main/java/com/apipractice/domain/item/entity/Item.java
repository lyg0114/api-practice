package com.apipractice.domain.item.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.apipractice.domain.common.BaseTimeEntity;
import com.apipractice.domain.item.entity.detail.Album;
import com.apipractice.domain.item.entity.detail.Book;
import com.apipractice.domain.item.entity.detail.Movie;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.entity
 * @since : 18.05.24
 */
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

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "album_id")
  private Album album;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "movie_id")
  private Movie movie;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "book_id")
  private Book book;
}
