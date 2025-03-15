package com.server.side.item.dto;

import com.server.side.item.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRegistrationRequest {

    private String name;

    private int price;

    private String category;

    private String image;

    private String information;

    private String measurement;

    public Item toEntity() {
        return Item.builder()
                .name(this.name)
                .price(this.price)
                .category(this.category)
                .image(this.image)
                .information(this.information)
                .measurement(this.measurement)
                .build();
    }

}
