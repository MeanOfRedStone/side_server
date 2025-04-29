package com.server.side.item.dto;

import com.server.side.item.domain.Item;
import com.server.side.item.domain.ItemDetail;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemDetailRegistrationRequest {

    private Item item;

    private String option;

    private int quantity;

    public ItemDetail toEntity() {
        return ItemDetail.builder()
                .item(this.item)
                .option(this.option)
                .quantity(this.quantity)
                .build();
    }

    public ItemDetailDTO toResponse() {
        return ItemDetailDTO.builder()
                .item(this.item)
                .option(this.option)
                .quantity(this.quantity)
                .build();
    }
}
