package com.server.side.itemDetail.impl;

import com.server.side.item.domain.Item;
import com.server.side.itemDetail.domain.ItemDetail;
import com.server.side.itemDetail.domain.ItemDetailRepository;
import com.server.side.itemDetail.dto.ItemDetailRegistrationRequest;
import com.server.side.itemDetail.service.impl.ItemDetailServiceImpl;
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

@ExtendWith(MockitoExtension.class)
public class ItemDetailServiceTest {

    @InjectMocks
    ItemDetailServiceImpl itemDetailService;
    @Mock
    ItemDetailRepository itemDetailRepository;

    @Test
    void addItemDetailThenReturnSame() {
        Item item = Item.builder()
                .id(1L)
                .name("셔츠")
                .price(1000)
                .category("상의")
                .image("셔츠.png")
                .information("멋진 셔츠")
                .measurement("L - 100")
                .build();
        ItemDetailRegistrationRequest request1 = ItemDetailRegistrationRequest.builder()
                                                                            .item(item)
                                                                            .option("L")
                                                                            .quantity(100)
                                                                            .build();

        given(itemDetailRepository.save(any(ItemDetail.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        assertEquals(request1.toEntity(), itemDetailService.addItemDetail(request1));

        verify(itemDetailRepository, times(1)).save(request1.toEntity());

        ItemDetailRegistrationRequest request2 = ItemDetailRegistrationRequest.builder()
                .item(item)
                .option("M")
                .quantity(50)
                .build();

        assertEquals(request2.toEntity(), itemDetailService.addItemDetail(request2));

        verify(itemDetailRepository, times(1)).save(request2.toEntity());
    }


}
