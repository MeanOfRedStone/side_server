package com.server.side.item.controller;

import com.google.gson.Gson;
import com.server.side.item.dto.ItemDto;
import com.server.side.item.dto.ItemRegistrationRequest;
import com.server.side.item.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.server.side.item.dto.ItemDto.fromEntity;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemServiceImpl itemService;

    @Autowired
    private Gson gson;

    @Test
    void createItemThenReturnSameItem() throws Exception {
        ItemRegistrationRequest request1 = ItemRegistrationRequest.builder()
                .name("셔츠")
                .price(1000)
                .category("상의")
                .image("셔츠.png")
                .information(List.of("img1", "img2"))
                .build();
        ItemDto dto1 = request1.toResponse();

        given(itemService.addItem(eq(request1)))
                .willReturn(fromEntity(request1.toEntity()));

        ResultActions actions = mockMvc.perform(
                post("/items")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(request1))
        );

        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(dto1.getName()))
                .andExpect(jsonPath("$.price").value(dto1.getPrice()))
                .andExpect(jsonPath("$.category").value(dto1.getCategory()))
                .andExpect(jsonPath("$.image").value(dto1.getImage()))
                .andExpect(jsonPath("$.information", containsInAnyOrder(dto1.getInformation().toArray(new String[0]))));
    }
}
