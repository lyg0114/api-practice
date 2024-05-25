package com.apipractice.domain.item.application.service;

import com.apipractice.domain.item.application.repository.ItemRepositroy;
import com.apipractice.domain.item.dto.ItemDto.ItemRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  @Transactional
  public void addItem(ItemRequest itemRequest) {
  }
}
