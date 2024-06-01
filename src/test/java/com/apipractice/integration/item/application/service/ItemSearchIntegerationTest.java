package com.apipractice.integration.item.application.service;

import static com.apipractice.domain.item.dto.ItemType.ALBUM;
import static com.apipractice.domain.item.dto.ItemType.BOOK;
import static com.apipractice.domain.item.dto.ItemType.MOVIE;
import static org.assertj.core.api.Assertions.assertThat;

import com.apipractice.domain.item.application.repository.ItemRepositroy;
import com.apipractice.domain.item.application.service.ItemService;
import com.apipractice.domain.item.dto.ItemDto.ItemCondition;
import com.apipractice.domain.item.dto.ItemDto.ItemResponse;
import com.apipractice.domain.item.dto.ItemType;
import com.apipractice.domain.item.entity.Item;
import com.apipractice.domain.item.entity.detail.Album;
import com.apipractice.domain.item.entity.detail.Book;
import com.apipractice.domain.item.entity.detail.Movie;
import com.apipractice.domain.member.application.repository.MemberRepository;
import com.apipractice.domain.member.entity.Member;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.application.service
 * @since : 28.05.24
 *  - Item.name 따른 조회 테스트
 *  - Item.price 따른 조회 테스트
 *  - album.artist 따른 조회 테스트
 */
@ActiveProfiles("test")
@Transactional
@SpringBootTest
class ItemSearchIntegerationTest {

  @Autowired private ItemRepositroy itemRepositroy;
  @Autowired private MemberRepository memberRepository;
  @Autowired private ItemService itemService;
  @Autowired private EntityManager em;

  /*
    - seller : 3 명
    - album : 6 개
    - book  : 4 개
    - movie : 4 개
   */
  @BeforeEach
  public void beforeEach() {
    Member seller1 = memberRepository.save(createUser(1L));
    itemRepositroy.save(createAlbum(1L, seller1, 1000));
    itemRepositroy.save(createAlbum(2L, seller1, 1000));
    itemRepositroy.save(createBook(3L, seller1, 2500));
    itemRepositroy.save(createMovie(4L, seller1, 3000));

    Member seller2 = memberRepository.save(createUser(2L));
    itemRepositroy.save(createAlbum(5L, seller2, 1000));
    itemRepositroy.save(createAlbum(6L, seller2, 2000));
    itemRepositroy.save(createBook(7L, seller2, 2600));
    itemRepositroy.save(createMovie(8L, seller2, 4000));

    Member seller3 = memberRepository.save(createUser(3L));
    itemRepositroy.save(createAlbum(9L, seller3, 1000));
    itemRepositroy.save(createAlbum(10L, seller3, 2000));
    itemRepositroy.save(createBook(11L, seller3, 2700));
    itemRepositroy.save(createMovie(12L, seller3, 4000));
  }

  private Member createUser(Long id) {
    return Member.builder()
        .name("name-" + id)
        .nickname("nickname-" + id)
        .email("email" + id + "@gmail.com")
        .password("test1234!-" + id)
        .build();
  }

  private Item createAlbum(Long id, Member seller, int price) {
    Item album = createItem(id, seller, ALBUM, price);
    album.updateAlbum(Album.builder()
        .artist("artist-" + id)
        .etc("etc-" + id)
        .build());
    return album;
  }

  private Item createMovie(Long id, Member seller, int price) {
    Item movie = createItem(id, seller, MOVIE, price);
    movie.updateMovie(Movie.builder()
        .actor("actor-" + id)
        .director("director-" + id)
        .build());
    return movie;
  }

  private Item createBook(Long id, Member seller, int price) {
    Item book = createItem(id, seller, BOOK, price);
    book.updateBook(Book.builder()
        .isbn("isbn-" + id)
        .author("author" + id)
        .build());
    return book;
  }

  private Item createItem(Long id, Member seller, ItemType itemType, int price) {
    Item.ItemBuilder builder = Item.builder();
    builder.seller(seller);
    builder.itemType(itemType.getKey());
    builder.name("item-" + id);
    builder.price(new BigDecimal(price));
    builder.stockQuantity(10);
    return builder.build();
  }

  @DisplayName("전체 상품 조회에 성공한다.")
  @Test
  void find_items() {
    //given
    em.flush();
    em.clear();

    ItemCondition condition = ItemCondition.builder().build();
    PageRequest pageRequest = PageRequest.of(0, 20);

    //when
    Page<Item> items = itemRepositroy.searchItemsPage(condition, pageRequest);

    //then
    assertThat(items).isNotNull();
    List<Item> result = items.getContent();
    assertThat(result).isNotNull();
    assertThat(result.size()).isEqualTo(12);
  }

  @DisplayName("상품명으로 조회에 성공한다.")
  @Test
  void find_items_by_item_name() {
    //given
    em.flush();
    em.clear();

    ItemCondition condition = ItemCondition.builder()
        .name("item-2")
        .build();
    PageRequest pageRequest = PageRequest.of(0, 10);

    //when
    Page<ItemResponse> responses = itemService.searchItems(condition, pageRequest);

    //then
    List<ItemResponse> content = responses.getContent();
    assertThat(content).isNotNull();
    assertThat(content.size()).isEqualTo(1);
    assertThat(content.get(0).getName()).isEqualTo("item-2");
  }

  @DisplayName("가격 으로 조회에 성공한다.")
  @Test
  void find_items_by_price() {
    //given
    em.flush();
    em.clear();

    ItemCondition condition = ItemCondition.builder()
        .goePrice(new BigDecimal(2200))
        .loePrice(new BigDecimal(2800))
        .build();
    PageRequest pageRequest = PageRequest.of(0, 10);

    //when
    Page<ItemResponse> responses = itemService.searchItems(condition, pageRequest);

    //then
    List<ItemResponse> content = responses.getContent();
    assertThat(content).isNotNull();
    assertThat(content.size()).isEqualTo(3);
  }

  @DisplayName("album.artist 으로 조회에 성공한다.")
  @Test
  void find_items_by_album_artist() {
    //given
    em.flush();
    em.clear();

    ItemCondition condition = ItemCondition.builder()
        .artist("artist-2")
        .build();
    PageRequest pageRequest = PageRequest.of(0, 10);

    //when
    Page<ItemResponse> responses = itemService.searchItems(condition, pageRequest);

    //then
    List<ItemResponse> content = responses.getContent();

    assertThat(content).isNotNull();
    assertThat(content.size()).isEqualTo(1);
    assertThat(content.get(0).getAlbum().getArtist()).isEqualTo("artist-2");
  }
}