package com.apipractice.domain.item.application.repository;

import static com.apipractice.domain.item.entity.QItem.item;
import static com.apipractice.domain.item.entity.detail.QAlbum.album;
import static com.apipractice.domain.item.entity.detail.QBook.book;
import static com.apipractice.domain.item.entity.detail.QMovie.movie;
import static com.apipractice.domain.member.entity.QMember.member;
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

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.application.repository
 * @since : 25.05.24
 */
@RequiredArgsConstructor
public class ItemRepositroyImpl implements ItemRepositroyCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<Item> searchItemsPage(ItemCondition condition, Pageable pageable) {
    return PageableExecutionUtils
        .getPage(
            searchItems(condition, pageable),
            pageable,
            () -> itemsTotal(condition)
        );
  }

  public List<Item> searchItems(ItemCondition condition, Pageable pageable) {
    return queryFactory
        .select(item)
        .from(item)
        .leftJoin(item.album, album).fetchJoin()
        .leftJoin(item.movie, movie).fetchJoin()
        .leftJoin(item.book, book).fetchJoin()
        .leftJoin(item.seller, member).fetchJoin()
        .where(
            itemNameEq(condition.getName()),
            priceBetween(condition.getLoePrice(), condition.getGoePrice()),
            albumArtistEq(condition.getArtist())
        )
        .offset(pageable.getOffset())   // 어디서부터
        .limit(pageable.getPageSize())  // 한페이지에 몇개
        .fetch();
  }

  /**
   * @param condition
   * @return
   * fetch join을 사용하면 결과 집합의 크기와 상관없이 연관된 모든 데이터를 가져오게 되므로, 이는 카운트 쿼리와는 부적합.
   * 따라서 fetch join이 아닌 단순 join을 사용해야 함
   */
  public Long itemsTotal(ItemCondition condition) {
    return queryFactory
        .select(item.count())
        .from(item)
        .leftJoin(item.album, album)
        .leftJoin(item.movie, movie)
        .leftJoin(item.book, book)
        .leftJoin(item.seller, member)
        .where(
            itemNameEq(condition.getName()),
            priceBetween(condition.getLoePrice(), condition.getGoePrice()),
            albumArtistEq(condition.getArtist())
        )
        .fetchOne();
  }

  private BooleanExpression albumArtistEq(String artist) {
    return hasText(artist) ? item.album.artist.eq(artist) : null;
  }

  private BooleanExpression itemNameEq(String itemName) {
    return hasText(itemName) ? item.name.eq(itemName) : null;
  }

  private BooleanExpression priceBetween(BigDecimal priceLoe, BigDecimal priceGoe) {
    if (priceLoe == null || priceGoe == null) {
      return null;
    }

    return priceGoe(priceGoe).and(priceLoe(priceLoe));
  }

  private BooleanExpression priceLoe(BigDecimal priceLoe) {
    return priceLoe != null ? item.price.loe(priceLoe) : null;
  }

  private BooleanExpression priceGoe(BigDecimal ageGoe) {
    return ageGoe != null ? item.price.goe(ageGoe) : null;
  }
}
