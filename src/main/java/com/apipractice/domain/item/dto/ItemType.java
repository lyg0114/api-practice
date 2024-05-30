package com.apipractice.domain.item.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.dto
 * @since : 25.05.24
 */
@Getter
@RequiredArgsConstructor
public enum ItemType {

  ALBUM("album", "앨범"),
  MOVIE("movie", "영화"),
  BOOK("book", "책");

  private final String key;
  private final String title;

  public static ItemType fromKey(String key) {
    for (ItemType item : ItemType.values()) {
      if (item.getKey().equals(key)) {
        return item;
      }
    }
    throw new IllegalArgumentException("No enum constant with key " + key);
  }
}
