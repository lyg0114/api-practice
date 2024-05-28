package com.apipractice.domain.item.application.service;

import static com.apipractice.global.exception.CustomErrorCode.ITEM_NOT_EXIST;

import com.apipractice.domain.item.application.repository.CustomItemRepository;
import com.apipractice.domain.item.application.repository.ItemRepositroy;
import com.apipractice.domain.item.dto.ItemDto.ItemCondition;
import com.apipractice.domain.item.dto.ItemDto.ItemRequest;
import com.apipractice.domain.item.dto.ItemDto.ItemResponse;
import com.apipractice.domain.item.entity.Item;
import com.apipractice.global.exception.CustomException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.application.service
 * @since : 25.05.24
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ItemService {

  private final ItemRepositroy itemRepositroy;
  private final CustomItemRepository customItemRepository;


  @Transactional(readOnly = true)
  public Page<ItemResponse> searchItems(ItemCondition condition) {
    Page<ItemResponse> responses= customItemRepository.searchItems(condition);
    return null;
  }

  @Transactional(readOnly = true)
  public ItemResponse findItem(long itemId) {
    return itemRepositroy.findById(itemId)
        .orElseThrow(() -> new CustomException(ITEM_NOT_EXIST))
        .toDto();
  }

  public void addItem(ItemRequest itemRequest) {
    itemRepositroy.save(itemRequest.toEntity());
  }

  public void updateItem(ItemRequest itemRequest, Long itemId) {
  }


  public void deleteItem(Long itemId) {
  }

}
