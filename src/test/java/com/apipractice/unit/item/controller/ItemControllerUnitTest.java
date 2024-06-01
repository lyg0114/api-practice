package com.apipractice.unit.item.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.apipractice.domain.item.application.service.ItemService;
import com.apipractice.domain.item.dto.ItemDto.ItemResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author : iyeong-gyo
 * @package : com.apipractice.unit.item.controller
 * @since : 01.06.24
 */
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerUnitTest {

  @MockBean public ItemService itemService;
  @Autowired private MockMvc mockMvc;

  void createItemResponse(Long id, List<ItemResponse> result) {
    result.add(ItemResponse.builder()
        .name("item-" + id)
        .price(new BigDecimal(1000))
        .stockQuantity(10)
        .build());
  }

  private List<ItemResponse> createItemResponses() {
    List<ItemResponse> result = new ArrayList<>();
    for (int index = 0; index < 10; index++) {
      createItemResponse((long) index, result);
    }
    return result;
  }

  @DisplayName("상품조회 api 테스트")
  @Test
  void find_items() throws Exception {
    //given
    List<ItemResponse> items = createItemResponses();
    when(itemService.searchItems(any(), any()))
        .thenReturn(new PageImpl<>(items, PageRequest.of(0, 10), 10));

    //when, then
    mockMvc
        .perform(get("/api/items/v1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content.length()").value(items.size()))
        .andExpect(jsonPath("$.content[0].name").value(items.get(0).getName()))
        .andExpect(jsonPath("$.content[0].price").value(items.get(0).getPrice().intValue()))
        .andExpect(jsonPath("$.content[0].stockQuantity").value(items.get(0).getStockQuantity()))
        .andExpect(jsonPath("$.totalElements").value(10))
        .andExpect(jsonPath("$.totalPages").value(1))
        .andExpect(jsonPath("$.size").value(10))
        .andExpect(jsonPath("$.number").value(0))
        .andExpect(jsonPath("$.first").value(true))
        .andExpect(jsonPath("$.last").value(true));
  }
}
