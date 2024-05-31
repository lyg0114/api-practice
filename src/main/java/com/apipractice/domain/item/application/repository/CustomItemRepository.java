package com.apipractice.domain.item.application.repository;

import static com.apipractice.domain.item.entity.QItem.item;
import static com.apipractice.domain.item.entity.detail.QAlbum.album;
import static com.apipractice.domain.item.entity.detail.QBook.book;
import static com.apipractice.domain.item.entity.detail.QMovie.movie;
import static org.springframework.util.StringUtils.hasText;

import com.apipractice.domain.item.dto.ItemDto.ItemCondition;
import com.apipractice.domain.item.entity.Item;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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

  public Page<Item> searchItemsPage(ItemCondition condition, Pageable pageable) {
    return PageableExecutionUtils
        .getPage(
            searchItems(condition, pageable),
            pageable,
            () -> itemsTotal(condition)
        );
  }

  private List<Item> searchItems(ItemCondition condition, Pageable pageable) {
    return queryFactory
        .select(item)
        .from(item)
        .join(item.album, album).fetchJoin()
        .join(item.movie, movie).fetchJoin()
        .join(item.book, book).fetchJoin()
        .where(
            itemNameEq(condition.getName()),
            priceBetween(condition.getLoePrice(), condition.getGoePrice())
        )
        .offset(pageable.getOffset())   // 어디서부터
        .limit(pageable.getPageSize())  // 한페이지에 몇개
        .fetch();
  }

  public Long itemsTotal(ItemCondition condition) {
    return queryFactory
        .select(item.count())
        .from(item)
        .join(item.album, album).fetchJoin()
        .join(item.movie, movie).fetchJoin()
        .join(item.book, book).fetchJoin()
        .where(
            itemNameEq(condition.getName()),
            priceBetween(condition.getLoePrice(), condition.getGoePrice())
        )
        .fetchOne();
  }

  private BooleanExpression itemNameEq(String itemName) {
    return hasText(itemName) ? item.name.eq(itemName) : null;
  }

  private BooleanExpression priceBetween(BigDecimal priceLoe, BigDecimal priceGoe) {
    return priceLoe(priceLoe).and(priceGoe(priceGoe));
  }

  private BooleanExpression priceLoe(BigDecimal priceLoe) {
    return priceLoe != null ? item.price.loe(priceLoe) : null;
  }

  private BooleanExpression priceGoe(BigDecimal ageGoe) {
    return ageGoe != null ? item.price.goe(ageGoe) : null;
  }
}
