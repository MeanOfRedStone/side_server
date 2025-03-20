package com.server.side.item.dto;

import com.server.side.item.domain.Item;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
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

    public ItemDto toResponse() {
        return ItemDto.builder()
                .name(this.name)
                .price(this.price)
                .category(this.category)
                .image(this.image)
                .information(this.information)
                .measurement(this.measurement)
                .build();
    }
}
