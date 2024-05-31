package com.apipractice.domain.item.application.service;

import static com.apipractice.domain.item.dto.ItemType.fromKey;
import static com.apipractice.global.exception.CustomErrorCode.ITEM_NAME_ALREADY_EXIST;
import static com.apipractice.global.exception.CustomErrorCode.ITEM_NOT_EXIST;
import static com.apipractice.global.exception.CustomErrorCode.ITEM_TYPE_NOT_EXIST;
import static org.springframework.util.StringUtils.hasText;

import com.apipractice.domain.item.application.repository.ItemRepositroy;
import com.apipractice.domain.item.dto.ItemDto.ItemCondition;
import com.apipractice.domain.item.dto.ItemDto.ItemRequest;
import com.apipractice.domain.item.dto.ItemDto.ItemResponse;
import com.apipractice.domain.item.entity.Item;
import com.apipractice.domain.member.application.repository.MemberRepository;
import com.apipractice.domain.member.entity.Member;
import com.apipractice.global.exception.CustomException;
import com.apipractice.global.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.application.service
 * @since : 25.05.24
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ItemService {

  private final ItemRepositroy itemRepositroy;
  private final MemberRepository memberRepository;
  private final JwtService jwtService;

  @Transactional(readOnly = true)
  public Page<ItemResponse> searchItems(ItemCondition condition, Pageable pageable) {
    return itemRepositroy.searchItemsPage(condition, pageable)
        .map(ItemResponse::fromEntity);
  }

  @Transactional(readOnly = true)
  public ItemResponse findItem(long itemId) {
    return itemRepositroy.findById(itemId)
        .orElseThrow(() -> new CustomException(ITEM_NOT_EXIST))
        .toDto();
  }

  public void addItem(ItemRequest itemRequest) {
    validateDuplicateName(itemRequest.getName());
    validateExistItemType(itemRequest.getItemType());
    Member seller = memberRepository.findByEmail(jwtService.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("가입된 이메일이 존재하지 않습니다."));

    itemRepositroy.save(itemRequest.toEntity(seller));
  }

  public void updateItem(ItemRequest itemRequest, Long itemId) {
    String email = jwtService.getEmail();
    itemRepositroy.findById(itemId)
        .orElseThrow(() -> new CustomException(ITEM_NOT_EXIST))
        .updateItem(itemRequest, email);
  }

  public void deleteItem(Long itemId) {
    Item item = itemRepositroy.findById(itemId)
        .orElseThrow(() -> new CustomException(ITEM_NOT_EXIST));

    itemRepositroy.delete(item);
  }

  private void validateDuplicateName(String name) {
    if (hasText(name) && itemRepositroy.existsByName(name)) {
      throw new CustomException(ITEM_NAME_ALREADY_EXIST);
    }
  }

  private void validateExistItemType(String itemType) {
    try {
      fromKey(itemType);
    } catch (IllegalArgumentException ex) {
      throw new CustomException(ITEM_TYPE_NOT_EXIST, ex);
    }
  }
}
