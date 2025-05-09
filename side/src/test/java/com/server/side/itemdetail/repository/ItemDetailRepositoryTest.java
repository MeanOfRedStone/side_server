package com.server.side.itemdetail.repository;

import com.server.side.item.domain.Item;
import com.server.side.item.dto.ItemDTO;
import com.server.side.item.repository.ItemRepository;
import com.server.side.itemdetail.domain.ItemDetail;
import com.server.side.itemdetail.dto.ItemDetailRegistrationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ItemDetailRepositoryTest {

    @Autowired
    private ItemDetailRepository itemDetailRepository;
    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("유효한 itemId를 주면, 해당 itemId를 가진 모든 ItemDetail을 반환한다")
    @Test
    void givenValidItemIdWhenFindAllByItemIdThenReturnsMatchingItems() {
        Item itemEntity = Item.builder()
                .name("셔츠")
                .price(1000)
                .category("상의")
                .description("멋진 셔츠")
                .image("셔츠.png")
                .information(List.of("img1", "img2"))
                .build();
        ItemDTO item = ItemDTO.fromEntity(itemRepository.save(itemEntity));
        ItemDetailRegistrationRequest detailRequest1 = ItemDetailRegistrationRequest.builder()
                .item(item)
                .option("L")
                .quantity(100)
                .build();
        List<ItemDetailRegistrationRequest> detailsRegistrationRequest1 = List.of(detailRequest1);
        List<ItemDetail> details1 = detailsRegistrationRequest1.stream()
                .map(detail -> ItemDetail.builder()
                        .item(itemEntity)
                        .option(detail.getOption())
                        .quantity(detail.getQuantity())
                        .build())
                .toList();

        List<ItemDetail> expected = new ArrayList<>();
        List<ItemDetail> saved1 = itemDetailRepository.saveAll(details1);
        expected.addAll(saved1);
        List<ItemDetail> result1 = itemDetailRepository.findAllByItemId(item.getId());

        assertThat(result1).containsExactlyInAnyOrderElementsOf(expected);

        ItemDetailRegistrationRequest detailRequest2 = ItemDetailRegistrationRequest.builder()
                .item(item)
                .option("M")
                .quantity(50)
                .build();
        List<ItemDetailRegistrationRequest> detailsRegistrationRequest2 = List.of(detailRequest2);
        List<ItemDetail> details2 = detailsRegistrationRequest2.stream()
                .map(detail -> ItemDetail.builder()
                        .item(itemEntity)
                        .option(detail.getOption())
                        .quantity(detail.getQuantity())
                        .build())
                .toList();


        List<ItemDetail> saved2 = itemDetailRepository.saveAll(details2);
        expected.addAll(saved2);
        List<ItemDetail> result2 = itemDetailRepository.findAllByItemId(item.getId());

        assertThat(result2).containsExactlyInAnyOrderElementsOf(expected);
    }
}
