package com.server.side.item.service.impl;

import com.server.side.item.domain.Item;
import com.server.side.item.dto.ItemDTO;
import com.server.side.item.dto.ItemRegistrationRequest;
import com.server.side.item.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @InjectMocks
    ItemServiceImpl itemService;
    @Mock
    ItemRepository itemRepository;

    @Test
    void addItemWithDetailsThenReturnSame() {
        ItemRegistrationRequest request1 = ItemRegistrationRequest.builder()
                .name("셔츠")
                .price(1000)
                .category("상의")
                .description("멋진 셔츠")
                .image("셔츠.png")
                .information(List.of("설명1.png", "설명2.png"))
                .build();

        given(itemRepository.save(any(Item.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ItemDTO result1 = itemService.addItem(request1);
        assertItem(ItemDTO.fromEntity(request1.toEntity()), result1);
        verify(itemRepository, times(1)).save(any(Item.class));

        ItemRegistrationRequest request2 = ItemRegistrationRequest.builder()
                .name("청바지")
                .price(2000)
                .category("하의")
                .description("멋진 바지")
                .image("청바지.png")
                .information(List.of("설명3.png", "설명4.png"))
                .build();

        ItemDTO result2 = itemService.addItem(request2);
        assertItem(ItemDTO.fromEntity(request2.toEntity()), result2);

        verify(itemRepository, times(2)).save(any(Item.class));
    }

    private void assertItem(ItemDTO expected, ItemDTO result) {
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getPrice(), result.getPrice());
        assertEquals(expected.getCategory(), result.getCategory());
        assertEquals(expected.getDescription(), result.getDescription());
        assertEquals(expected.getImage(), result.getImage());
        assertEquals(expected.getInformation().size(), result.getInformation().size());
        for(int i = 0; i < expected.getInformation().size(); i++) {
            assertEquals(expected.getInformation().get(i), result.getInformation().get(i));
        }
    }
}
