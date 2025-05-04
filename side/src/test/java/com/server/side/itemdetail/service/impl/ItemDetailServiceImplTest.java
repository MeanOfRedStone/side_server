package com.server.side.itemdetail.service.impl;

import com.server.side.itemdetail.domain.ItemDetail;
import com.server.side.item.dto.ItemDTO;
import com.server.side.itemdetail.dto.ItemDetailRegistrationRequest;
import com.server.side.itemdetail.repository.ItemDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.server.side.itemdetail.dto.ItemDetailDTO.fromEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ItemDetailServiceImplTest {

    @InjectMocks
    ItemDetailServiceImpl itemDetailService;
    @Mock
    ItemDetailRepository itemDetailRepository;

    @Test
    void addItemDetailThenReturnSame() {
        ItemDTO item = ItemDTO.builder()
                .id(1L)
                .name("셔츠")
                .price(1000)
                .category("상의")
                .image("셔츠.png")
                .information(List.of("img1", "img2"))
                .build();
        ItemDetailRegistrationRequest request1 = ItemDetailRegistrationRequest.builder()
                                                                            .id(1L)
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
