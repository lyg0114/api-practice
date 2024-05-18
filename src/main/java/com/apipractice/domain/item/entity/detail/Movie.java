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
@DiscriminatorValue("movie")
@Getter
@Setter
@Entity
public class Movie extends Item {

  private String director;
  private String actor;
}
