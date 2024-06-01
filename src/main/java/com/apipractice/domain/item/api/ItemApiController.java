package com.apipractice.domain.item.api;

import com.apipractice.domain.item.application.service.ItemService;
import com.apipractice.domain.item.dto.ItemDto;
import com.apipractice.domain.item.dto.ItemDto.ItemResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public ResponseEntity<Page<ItemResponse>> searchItems(ItemDto.ItemCondition condition, Pageable pageable) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(itemService.searchItems(condition, pageable));
  }

  @GetMapping("/{itemId}")
  public ResponseEntity<ItemResponse> getQuestion(@PathVariable long itemId) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(itemService.findItem(itemId));
  }

  @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
  @PostMapping
  public ResponseEntity<Void> addItem(@RequestBody @Valid ItemDto.ItemRequest itemRequest) {

    itemService.addItem(itemRequest);
    return ResponseEntity.ok().build();
  }

  @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
  @PatchMapping("/{itemId}")
  public ResponseEntity<ItemResponse> updateItem(
      @PathVariable Long itemId,
      @RequestBody @Valid ItemDto.ItemRequest itemRequest) {

    itemService.updateItem(itemRequest, itemId);
    ItemResponse item = itemService.findItem(itemId);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(item);
  }

  @PreAuthorize("hasAnyRole('ADMIN','SELLER')")
  @DeleteMapping("/{itemId}")
  public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {

    itemService.deleteItem(itemId);
    return ResponseEntity.ok()
        .build();
  }
}
