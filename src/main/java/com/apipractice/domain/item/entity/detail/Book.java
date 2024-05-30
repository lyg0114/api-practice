package com.apipractice.domain.item.entity.detail;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.util.StringUtils.hasText;

import com.apipractice.domain.common.BaseTimeEntity;
import com.apipractice.domain.item.dto.ItemDto;
import com.apipractice.domain.item.dto.ItemDto.BookItemRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * @author : iyeong-gyo
 * @package : com.example.jpashopyglee.domain
 * @since : 12.05.24
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Table(name = "book")
@Entity
public class Book extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "book_id")
  private Long id;

  @Column(name = "author")
  private String author;

  @Column(name = "isbn")
  private String isbn;

  public void updateBook(BookItemRequest book) {
    if (hasText(book.getAuthor())) {
      this.author = book.getAuthor();
    }
    if (hasText(book.getIsbn())) {
      this.isbn = book.getIsbn();
    }
  }
}
