package com.server.side.item.dto;

import com.server.side.item.domain.Item;
import com.server.side.item.domain.ItemDetail;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemDetailDTO {

    private long id;

    private Item item;

    private String option;

    private int quantity;

    public static ItemDetailDTO fromEntity(ItemDetail itemDetail) {
        return ItemDetailDTO.builder()
                .id(itemDetail.getId())
                .item(itemDetail.getItem())
                .option(itemDetail.getOption())
                .quantity(itemDetail.getQuantity())
                .build();
    }
}
