package com.apipractice.integration.item.application.service;

import static com.apipractice.domain.item.dto.ItemType.ALBUM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.apipractice.domain.item.application.repository.CustomItemRepository;
import com.apipractice.domain.item.application.repository.ItemRepositroy;
import com.apipractice.domain.item.application.service.ItemService;
import com.apipractice.domain.item.dto.ItemDto.AlbumItemRequest;
import com.apipractice.domain.item.dto.ItemDto.ItemRequest;
import com.apipractice.domain.item.dto.ItemDto.ItemResponse;
import com.apipractice.domain.item.dto.ItemType;
import com.apipractice.domain.item.entity.Item;
import com.apipractice.domain.item.entity.detail.Album;
import com.apipractice.domain.member.application.repository.MemberRepository;
import com.apipractice.domain.member.entity.Member;
import com.apipractice.global.security.service.JwtService;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.application.service
 * @since : 28.05.24
 */
@ActiveProfiles("test")
@SpringBootTest
class ItemServiceIntegerationTest {

  @Autowired private ItemService itemService;
  @Autowired private ItemRepositroy itemRepositroy;
  @Autowired private MemberRepository memberRepository;
  @Autowired private CustomItemRepository customItemRepository;
  @Autowired EntityManager em;

  private Member createUser(Long id) {
    return Member.builder()
        .name("name -" + id)
        .nickname("nickname -" + id)
        .email("email" + id + "@gmail.com")
        .password("test1234! - " + id)
        .build();
  }

  private Item createAlbum(Long id, Member seller) {
    Item album = createItem(id, seller, ALBUM);
    album.updateAlbum(Album.builder()
        .artist("artist - " + id)
        .etc("etc - " + id)
        .build());
    return album;
  }

  private Item createItem(Long id, Member seller, ItemType itemType) {
    Item.ItemBuilder builder = Item.builder();
    builder.seller(seller);
    builder.itemType(itemType.getKey());
    builder.name("item - " + id);
    builder.price(new BigDecimal(10000));
    builder.stockQuantity(10);
    return builder.build();
  }

  /**
   * 물품을 등록 후, 검증을 위한 조회 쿼리 실행시 트랜잭션이 종료 되므로 @Transactional 을 추가
   */
  @Transactional
  @DisplayName("물품 단건 등록에 성공한다.")
  @Test
  void insert_item_test() {
    //given
    Member seller = createUser(1L);
    memberRepository.save(seller);

    //TODO : 추후 Spring Security 테스트로 적용
    JwtService jwtService = mock(JwtService.class);
    when(jwtService.getEmail()).thenReturn(seller.getEmail());
    itemService = new ItemService(itemRepositroy, memberRepository, customItemRepository, jwtService);

    String name = "박효신 1집";
    BigDecimal price = new BigDecimal("50000");
    int stockQuantity = 10;
    String itemType = ALBUM.getKey();
    String artist = "박효신";
    String etc = "박효신 1집 한정 앨범";
    ItemRequest hyosinAlbum = ItemRequest.builder()
        .name(name)
        .price(price)
        .stockQuantity(stockQuantity)
        .itemType(itemType)
        .album(AlbumItemRequest.builder()
            .artist(artist)
            .etc(etc)
            .build())
        .build();

    //when
    itemService.addItem(hyosinAlbum);
    ItemResponse response = itemService.findItem(1L);

    //then
    assertThat(response.getName()).isEqualTo(name);
    assertThat(response.getPrice()).isEqualTo(price);
    assertThat(response.getStockQuantity()).isEqualTo(stockQuantity);
    assertThat(response.getItemType()).isEqualTo(itemType);
    assertThat(response.getSellerEamil()).isEqualTo(seller.getEmail());
    assertThat(response.getSellerName()).isEqualTo(seller.getName());
    assertThat(response.getBook()).isNull();
    assertThat(response.getMovie()).isNull();
    assertThat(response.getAlbum()).isNotNull();
    assertThat(response.getAlbum().getArtist()).isEqualTo(artist);
    assertThat(response.getAlbum().getEtc()).isEqualTo(etc);
  }

  @DisplayName("물품 단건 조회에 성공한다.")
  @Test
  void find_item_test() {
    //given
    Member seller = createUser(1L);
    memberRepository.save(seller);
    Item album = createAlbum(1L, seller);
    itemRepositroy.save(album);

    //when
    ItemResponse response = itemService.findItem(1L);

    //then
    assertThat(response.getName()).isEqualTo(album.getName());
    assertThat(response.getPrice()).isEqualTo(new BigDecimal(album.getPrice() + ".00"));
    assertThat(response.getStockQuantity()).isEqualTo(album.getStockQuantity());
    assertThat(response.getItemType()).isEqualTo(album.getItemType());
    assertThat(response.getSellerEamil()).isEqualTo(album.getSeller().getEmail());
    assertThat(response.getSellerName()).isEqualTo(album.getSeller().getName());
    assertThat(response.getBook()).isNull();
    assertThat(response.getMovie()).isNull();
    assertThat(response.getAlbum()).isNotNull();
    assertThat(response.getAlbum().getArtist()).isEqualTo(album.getAlbum().getArtist());
    assertThat(response.getAlbum().getEtc()).isEqualTo(album.getAlbum().getEtc());
  }
}