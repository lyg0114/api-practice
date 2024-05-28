package com.apipractice.integration.item.application.service;

import static com.apipractice.domain.item.dto.ItemType.ALBUM;
import static org.assertj.core.api.Assertions.assertThat;

import com.apipractice.domain.item.application.repository.ItemRepositroy;
import com.apipractice.domain.item.application.service.ItemService;
import com.apipractice.domain.item.dto.ItemDto.ItemResponse;
import com.apipractice.domain.item.dto.ItemType;
import com.apipractice.domain.item.entity.Item;
import com.apipractice.domain.item.entity.detail.Album;
import com.apipractice.domain.member.application.repository.MemberRepository;
import com.apipractice.domain.member.entity.Member;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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