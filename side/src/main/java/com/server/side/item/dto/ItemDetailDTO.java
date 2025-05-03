package com.server.side.item.dto;

import com.server.side.item.domain.ItemDetail;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemDetailDTO {

    private long id;

    private ItemDTO item;

    private String option;

    private int quantity;

    public static ItemDetailDTO fromEntity(ItemDetail itemDetail) {
        return ItemDetailDTO.builder()
                .id(itemDetail.getId())
                .item(ItemDTO.fromEntity(itemDetail.getItem()))
                .option(itemDetail.getOption())
                .quantity(itemDetail.getQuantity())
                .build();
    }
}
