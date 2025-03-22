package com.server.side.item.service.impl;

import com.server.side.item.domain.Item;
import com.server.side.item.domain.ItemRepository;
import com.server.side.item.dto.ItemRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void addItemThenReturnSame() {
        ItemRegistrationRequest request1 = ItemRegistrationRequest.builder()
                .name("셔츠")
                .price(1000)
                .category("상의")
                .image("셔츠.png")
                .information("멋진 셔츠")
                .measurement("L - 100")
                .build();

        given(itemRepository.save(any(Item.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        assertEquals(request1.toEntity(), itemService.addItem(request1));
        verify(itemRepository, times(1)).save(request1.toEntity());

        ItemRegistrationRequest request2 = ItemRegistrationRequest.builder()
                .name("청바지")
                .price(2000)
                .category("하의")
                .image("청바지.png")
                .information("멋진 청바지")
                .measurement("XL - 32")
                .build();

        assertEquals(request2.toEntity(), itemService.addItem(request2));
        verify(itemRepository, times(1)).save(request2.toEntity());

    }
}
