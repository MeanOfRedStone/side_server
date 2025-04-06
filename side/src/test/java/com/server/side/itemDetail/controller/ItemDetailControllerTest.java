package com.server.side.itemDetail.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.server.side.config.InstantTypeAdapter;
import com.server.side.item.domain.Item;
import com.server.side.itemDetail.dto.ItemDetailDTO;
import com.server.side.itemDetail.dto.ItemDetailRegistrationRequest;
import com.server.side.itemDetail.service.impl.ItemDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.util.List;

import static com.server.side.itemDetail.dto.ItemDetailDTO.fromEntity;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WebMvcTest(ItemDetailController.class)
public class ItemDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemDetailServiceImpl itemDetailService;

    private static Gson gson;

    @BeforeAll
    public static void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();
    }

    @Test
    void createItemDetailThenReturnSameItemDetail() throws Exception{
        Item item = Item.builder()
                .id(1L)
                .name("셔츠")
                .price(1000)
                .category("상의")
                .image("셔츠.png")
                .information(List.of("img1", "img2"))
                .build();

        ItemDetailRegistrationRequest request1 = ItemDetailRegistrationRequest.builder()
                .item(item)
                .option("L")
                .quantity(100)
                .build();
        ItemDetailDTO dto1 = request1.toResponse();

        given(itemDetailService.addItemDetail(eq(request1))).willReturn(fromEntity(request1.toEntity()));

        ResultActions actions = mockMvc.perform(
                post("/items/{itemId}/details", item.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request1))
        );

        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.item.id").value(dto1.getItem().getId()))
                .andExpect(jsonPath("$.option").value(dto1.getOption()))
                .andExpect(jsonPath("$.quantity").value(dto1.getQuantity()));

        ItemDetailRegistrationRequest request2 = ItemDetailRegistrationRequest.builder()
                .item(item)
                .option("M")
                .quantity(200)
                .build();
        ItemDetailDTO dto2 = request2.toResponse();

        given(itemDetailService.addItemDetail(eq(request2))).willReturn(fromEntity(request2.toEntity()));

        ResultActions actions2 = mockMvc.perform(
                post("/items/{itemId}/details", item.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request2))
        );

        actions2
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.item.id").value(dto2.getItem().getId()))
                .andExpect(jsonPath("$.option").value(dto2.getOption()))
                .andExpect(jsonPath("$.quantity").value(dto2.getQuantity()));
    }
}
