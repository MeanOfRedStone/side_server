package com.server.side.itemDetail.impl;

import com.server.side.item.domain.Item;
import com.server.side.item.domain.ItemDetail;
import com.server.side.item.repository.ItemDetailRepository;
import com.server.side.item.dto.ItemDetailRegistrationRequest;
import com.server.side.item.service.impl.ItemDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.server.side.item.dto.ItemDetailDTO.fromEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
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
                .information(List.of("img1", "img2"))
                .build();
        ItemDetailRegistrationRequest request1 = ItemDetailRegistrationRequest.builder()
                                                                            .item(item)
                                                                            .option("L")
                                                                            .quantity(100)
                                                                            .build();

        given(itemDetailRepository.save(any(ItemDetail.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        assertEquals(fromEntity(request1.toEntity()), itemDetailService.addItemDetail(request1));

        verify(itemDetailRepository, times(1)).save(request1.toEntity());

        ItemDetailRegistrationRequest request2 = ItemDetailRegistrationRequest.builder()
                .item(item)
                .option("M")
                .quantity(50)
                .build();

        assertEquals(fromEntity(request2.toEntity()), itemDetailService.addItemDetail(request2));

        verify(itemDetailRepository, times(1)).save(request2.toEntity());
    }


}
