package com.apipractice.domain.item.application.service;

import com.apipractice.domain.item.application.repository.CustomItemRepository;
import com.apipractice.domain.item.application.repository.ItemRepositroy;
import com.apipractice.domain.item.dto.ItemDto.ItemCondition;
import com.apipractice.domain.item.dto.ItemDto.ItemRequest;
import com.apipractice.domain.item.dto.ItemDto.ItemResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public void addItem(ItemRequest itemRequest) {
    itemRepositroy.save(itemRequest.toEntity());
  }

  @Transactional(readOnly = true)
  public Page<ItemResponse> searchItems(ItemCondition condition) {
    Page<ItemResponse> responses= customItemRepository.searchItems(condition);
    return null;
  }
}
