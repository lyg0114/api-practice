package com.apipractice.domain.item.application.repository;

import com.apipractice.domain.item.dto.ItemDto.ItemCondition;
import com.apipractice.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.application.repository
 * @since : 25.05.24
 */
public interface ItemRepositroyCustom {

  Page<Item> searchItemsPage(ItemCondition condition, Pageable pageable);
}
