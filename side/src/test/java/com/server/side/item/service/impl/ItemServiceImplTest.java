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

import static com.server.side.item.dto.ItemDTO.fromEntity;
import static org.assertj.core.api.Assertions.assertThat;
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
                .build();
        String image1 = "셔츠.png";
        List<String> information1 = List.of("설명1.png", "설명2.png");

        given(itemRepository.save(any(Item.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ItemDTO expected1 = ItemDTO.builder()
                .name(request1.getName())
                .price(request1.getPrice())
                .category(request1.getCategory())
                .description(request1.getDescription())
                .image(image1)
                .information(information1)
                .build();
        ItemDTO result1 = itemService.addItem(request1, image1, information1);
        assertItem(expected1, result1);
        verify(itemRepository, times(1)).save(any(Item.class));

        ItemRegistrationRequest request2 = ItemRegistrationRequest.builder()
                .name("청바지")
                .price(2000)
                .category("하의")
                .description("멋진 바지")
                .build();
        String image2= "청바지.png";
        List<String> information2 = List.of("설명3.png", "설명4.png");

        ItemDTO expected2 = ItemDTO.builder()
                .name(request2.getName())
                .price(request2.getPrice())
                .category(request2.getCategory())
                .description(request2.getDescription())
                .image(image2)
                .information(information2)
                .build();
        ItemDTO result2 = itemService.addItem(request2, image2, information2);
        assertItem(expected2, result2);

        verify(itemRepository, times(2)).save(any(Item.class));
    }

    @Test
    void findAllItemsThenReturnAllItemDTOs() {
        ItemRegistrationRequest request1 = ItemRegistrationRequest.builder()
                .id(1L)
                .name("셔츠")
                .price(1000)
                .category("상의")
                .description("멋진 셔츠")
                .image("셔츠.png")
                .information(List.of("img1", "img2"))
                .build();

        ItemRegistrationRequest request2 = ItemRegistrationRequest.builder()
                .id(2L)
                .name("청바지")
                .price(2000)
                .category("하의")
                .description("멋진 바지")
                .image("청바지.png")
                .information(List.of("img1", "img2"))
                .build();

        List<Item> items1 = List.of(request1.toEntity(), request2.toEntity());
        given(itemRepository.findAll()).willReturn(items1);

        List<ItemDTO> expected1 = List.of(fromEntity(request1.toEntity()), fromEntity(request2.toEntity()));
        List<ItemDTO> result1 = itemService.searchAllItems();

        assertEquals(expected1.size(), result1.size());
        for(int i = 0; i < expected1.size(); i++) {
            assertItem(expected1.get(i), result1.get(i));
        }

        verify(itemRepository, times(1)).findAll();

        List<Item> items2 = null;
        given(itemRepository.findAll()).willReturn(items2);
        List<ItemDTO> expected2 = null;
        List<ItemDTO> result2 = itemService.searchAllItems();

        assertThat(expected2 == null);
        assertThat(result2 == null);

        verify(itemRepository, times(2)).findAll();
    }

    private void assertItem(ItemDTO expected, ItemDTO result) {
        assertEquals(expected.getId(), result.getId());
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
