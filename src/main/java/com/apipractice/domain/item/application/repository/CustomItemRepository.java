package com.apipractice.domain.item.application.repository;

import com.apipractice.domain.item.dto.ItemDto.ItemCondition;
import com.apipractice.domain.item.dto.ItemDto.ItemResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.application.repository
 * @since : 25.05.24
 */
@RequiredArgsConstructor
@Repository
public class CustomItemRepository {

  private final JPAQueryFactory queryFactory;

  public Page<ItemResponse> searchItems(ItemCondition condition) {
    return null;
  }
}
