package com.server.side.itemDetail.dto;

import com.server.side.item.domain.Item;
import com.server.side.itemDetail.domain.ItemDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
