package com.apipractice.integration.item.application.service;

import static com.apipractice.domain.item.dto.ItemType.ALBUM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.apipractice.global.security.type.RoleType;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.application.service
 * @since : 28.05.24
 */
@ActiveProfiles("test")
@Transactional // 모든 테스트가 각각의 트랜잭션에서 실행되도록
@SpringBootTest
class ItemCRUDServiceIntegerationTest {

  @Autowired private JwtService jwtService;
  @Autowired private ItemRepositroy itemRepositroy;
  @Autowired private MemberRepository memberRepository;
  @Autowired private ItemService itemService;
  @Autowired private EntityManager em;

  private Member createUser(Long id) {
    return Member.builder()
        .name("name-" + id)
        .nickname("nickname-" + id)
        .email("email" + id + "@gmail.com")
        .password("test1234!-" + id)
        .build();
  }

  private Item createAlbum(Long id, Member seller) {
    Item album = createItem(id, seller, ALBUM);
    album.updateAlbum(Album.builder()
        .artist("artist-" + id)
        .etc("etc-" + id)
        .build());
    return album;
  }

  private Item createItem(Long id, Member seller, ItemType itemType) {
    Item.ItemBuilder builder = Item.builder();
    builder.seller(seller);
    builder.itemType(itemType.getKey());
    builder.name("item-" + id);
    builder.price(new BigDecimal(10000));
    builder.stockQuantity(10);
    return builder.build();
  }

  private void loginUser(Member user) {
    String roleStrs = "ROLE_USER,ROLE_SELLER";
    String accessToken = jwtService.createAccessToken(user.getEmail(), roleStrs, user.getId());
    List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getKey()));
    Authentication authToken = new UsernamePasswordAuthenticationToken(user.getEmail(), accessToken, authorities);
    SecurityContextHolder.getContext().setAuthentication(authToken);
  }

  @DisplayName("물품 단건 조회에 성공한다.")
  @Test
  void find_item_test() {
    //given
    Member seller = memberRepository.save(createUser(1L));
    loginUser(seller);
    Item album = itemRepositroy.save(createAlbum(1L, seller));

    //when
    ItemResponse response = itemService.findItem(seller.getId());

    //then
    assertThat(response.getName()).isEqualTo(album.getName());
    assertThat(response.getPrice()).isEqualTo(album.getPrice());
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

  @DisplayName("물품 단건 등록에 성공한다.")
  @Test
  void insert_item_test() {
    //given
    Member seller = memberRepository.save(createUser(1L));
    loginUser(seller);

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

  @DisplayName("물품 단건 수정에 성공한다.")
  @Test
  void update_item_test() {
    //given
    Member seller = memberRepository.save(createUser(1L));
    loginUser(seller);

    String name = "박효신 1집";
    BigDecimal price = new BigDecimal("50000");
    int stockQuantity = 10;
    String itemType = ALBUM.getKey();
    String artist = "박효신";
    String etc = "박효신 1집 한정 앨범";

    Item item = Item.builder()
        .seller(seller)
        .name(name)
        .price(price)
        .stockQuantity(stockQuantity)
        .itemType(itemType)
        .album(Album.builder()
            .artist(artist)
            .etc(etc)
            .build())
        .build();

    Item saveItem = itemRepositroy.save(item);

    //when
    String updateName = "박효신 2집";
    BigDecimal updatePrice = new BigDecimal("100000");
    int updateStockQuantity = 20;
    String updateArtist = "##박효신##";
    String updateEtc = "##박효신 2집 한정 앨범##";
    ItemRequest updateAlbum = ItemRequest.builder()
        .name(updateName)
        .price(updatePrice)
        .stockQuantity(updateStockQuantity)
        .itemType(itemType)
        .album(AlbumItemRequest.builder()
            .artist(updateArtist)
            .etc(updateEtc)
            .build())
        .build();

    itemService.updateItem(updateAlbum, saveItem.getId());

    //then
    ItemResponse response = itemService.findItem(saveItem.getId());

    assertThat(response.getName()).isEqualTo(updateName);
    assertThat(response.getPrice()).isEqualTo(updatePrice);
    assertThat(response.getStockQuantity()).isEqualTo(updateStockQuantity);
    assertThat(response.getItemType()).isEqualTo(itemType);
    assertThat(response.getSellerEamil()).isEqualTo(seller.getEmail());
    assertThat(response.getSellerName()).isEqualTo(seller.getName());
    assertThat(response.getBook()).isNull();
    assertThat(response.getMovie()).isNull();
    assertThat(response.getAlbum()).isNotNull();
    assertThat(response.getAlbum().getArtist()).isEqualTo(updateArtist);
    assertThat(response.getAlbum().getEtc()).isEqualTo(updateEtc);
  }

  @DisplayName("물품 단건 삭제에 성공한다.")
  @Test
  void delete_item_test() {
    //given
    Member seller = memberRepository.save(createUser(1L));
    loginUser(seller);

    String name = "박효신 1집";
    BigDecimal price = new BigDecimal("50000");
    int stockQuantity = 10;
    String itemType = ALBUM.getKey();
    String artist = "박효신";
    String etc = "박효신 1집 한정 앨범";

    Item item = Item.builder()
        .seller(seller)
        .name(name)
        .price(price)
        .stockQuantity(stockQuantity)
        .itemType(itemType)
        .album(Album.builder()
            .artist(artist)
            .etc(etc)
            .build())
        .build();

    Item saveItem = itemRepositroy.save(item);
    Long itemId = saveItem.getId();

    itemRepositroy.flush();
    //when
    itemService.deleteItem(itemId);

    // 실제 쿼리 확인
    em.flush();
    em.clear();

    //then
    assertThrows(NoSuchElementException.class, () -> {
      itemRepositroy.findById(itemId).orElseThrow();
    });
  }
}