package com.server.side.item.repository;

import com.server.side.item.domain.Item;
import com.server.side.item.domain.ItemDetail;
import com.server.side.item.dto.ItemDetailRegistrationRequest;
import com.server.side.item.dto.ItemWithDetailsRegistrationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Rollback
public class ItemDetailRepositoryTest {

    @Autowired
    private ItemDetailRepository itemDetailRepository;
    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("유효한 itemId를 주면, 해당 itemId를 가진 모든 ItemDetail을 반환한다")
    @Test
    void givenValidItemIdWhenFindAllByItemIdThenReturnsMatchingItems() {
        ItemDetailRegistrationRequest detailRequest = ItemDetailRegistrationRequest.builder()
                .option("L")
                .quantity(100)
                .build();
        List<ItemDetailRegistrationRequest> detailsRegistrationRequest1 = List.of(detailRequest);
        ItemWithDetailsRegistrationRequest request1 = ItemWithDetailsRegistrationRequest.builder()
                .name("셔츠")
                .price(1000)
                .category("상의")
                .description("멋진 셔츠")
                .image("셔츠.png")
                .information(List.of("img1", "img2"))
                .itemDetails(detailsRegistrationRequest1)
                .build();

        Item item1 = itemRepository.save(request1.toItemEntity());

        List<ItemDetail> details1 = detailsRegistrationRequest1.stream()
                        .map(itemDetailRegistrationRequest -> {
                                itemDetailRegistrationRequest.setItem(item1);
                                return itemDetailRegistrationRequest.toEntity();
                        })
                                .collect(Collectors.toList());
        itemDetailRepository.saveAll(details1);

        List<ItemDetail> expected1 = itemDetailRepository.findAllByItemId(item1.getId());
        for(int i = 0; i < expected1.size(); i++) {
            assertEquals(details1.get(i).getId(), expected1.get(i).getId());
            assertEquals(details1.get(i).getQuantity(), expected1.get(i).getQuantity());
            assertEquals(details1.get(i).getOption(), expected1.get(i).getOption());
        }
    }
}
