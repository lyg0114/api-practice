package com.apipractice.domain.item.entity.detail;

import com.apipractice.domain.item.entity.Item;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.entity.detail
 * @since : 18.05.24
 */
@DiscriminatorValue("album")
@Getter
@Entity
public class Album extends Item {

  private String artist;
  private String etc;
}
