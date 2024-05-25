package com.apipractice.domain.item.api;

import com.apipractice.domain.item.application.service.ItemService;
import com.apipractice.domain.item.dto.ItemDto;
import com.apipractice.domain.item.dto.ItemDto.ItemResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.domain.item.api
 * @since : 25.05.24
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/items/v1")
@RestController
public class ItemApiController {

  private final ItemService itemService;

  @GetMapping
  public ResponseEntity<Page<ItemResponse>> searchItems(ItemDto.ItemCondition condition) {
    log.info("ItemDto.condition : {}", condition);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(itemService.searchItems(condition))
        ;
  }

  @PostMapping
  public ResponseEntity<Void> addItem(@RequestBody @Valid ItemDto.ItemRequest itemRequest) {
    log.info("ItemDto.ItemRequest : {}", itemRequest);
    itemService.addItem(itemRequest);
    return ResponseEntity.ok().build();
  }
}
