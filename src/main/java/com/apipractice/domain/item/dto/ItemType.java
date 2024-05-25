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
}
