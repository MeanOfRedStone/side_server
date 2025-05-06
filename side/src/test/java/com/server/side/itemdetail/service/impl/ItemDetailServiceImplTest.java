package com.server.side.itemdetail.service.impl;

import com.server.side.item.dto.ItemDTO;
import com.server.side.itemdetail.domain.ItemDetail;
import com.server.side.itemdetail.dto.ItemDetailDTO;
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
import static org.mockito.ArgumentMatchers.anyList;
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
    void addAllItemDetailThenReturnSame() {
        ItemDTO item1 = ItemDTO.builder()
                .id(1L)
                .name("셔츠")
                .price(1000)
                .category("상의")
                .description("멋진 셔츠")
                .image("셔츠.png")
                .information(List.of("img1", "img2"))
                .build();
        ItemDetailRegistrationRequest request1 = ItemDetailRegistrationRequest.builder()
                                                                            .item(item1)
                                                                            .option("L")
                                                                            .quantity(100)
                                                                            .build();
        ItemDetailRegistrationRequest request2 = ItemDetailRegistrationRequest.builder()
                .item(item1)
                .option("M")
                .quantity(50)
                .build();

        given(itemDetailRepository.saveAll(anyList()))
                .willAnswer(invocation -> {
                    @SuppressWarnings("unchecked")
                    List<ItemDetail> savedList = invocation.getArgument(0);
                    return savedList;
                });


        List<ItemDetailDTO> expected1 = List.of(fromEntity(request1.toEntity()), fromEntity(request2.toEntity()));
        List<ItemDetailDTO> result1 = itemDetailService.addAllItemDetails(List.of(request1, request2));

        assertItemDetails(expected1, result1);
        verify(itemDetailRepository, times(1)).saveAll(List.of(request1.toEntity(), request2.toEntity()));

        ItemDTO item2 = ItemDTO.builder()
                .id(2L)
                .name("청바지")
                .price(1000)
                .category("하의")
                .description("멋진 청바지")
                .image("청바지.png")
                .information(List.of("img3", "img4"))
                .build();
        ItemDetailRegistrationRequest request3 = ItemDetailRegistrationRequest.builder()
                .item(item2)
                .option("32")
                .quantity(60)
                .build();
        ItemDetailRegistrationRequest request4 = ItemDetailRegistrationRequest.builder()
                .item(item2)
                .option("30")
                .quantity(70)
                .build();

        List<ItemDetailDTO> expected2 = List.of(fromEntity(request3.toEntity()), fromEntity(request4.toEntity()));
        List<ItemDetailDTO> result2 = itemDetailService.addAllItemDetails(List.of(request3, request4));

        assertItemDetails(expected2, result2);
        verify(itemDetailRepository, times(1)).saveAll(List.of(request3.toEntity(), request4.toEntity()));
    }

    private void assertItemDetails(List<ItemDetailDTO> expected, List<ItemDetailDTO> result) {
        assertEquals(expected.size(), result.size());
        for(int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getItem(), result.get(i).getItem());
            assertEquals(expected.get(i).getOption(), result.get(i).getOption());
            assertEquals(expected.get(i).getQuantity(), result.get(i).getQuantity());
        }
    }

}
