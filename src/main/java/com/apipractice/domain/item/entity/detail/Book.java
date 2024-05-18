package com.apipractice.domain.item.entity.detail;

import com.apipractice.domain.item.entity.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : iyeong-gyo
 * @package : com.example.jpashopyglee.domain
 * @since : 12.05.24
 */
@DiscriminatorValue("book")
@Getter
@Setter
@Entity
public class Book extends Item {

  private String author;
  private String isbn;
}
