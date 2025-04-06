package com.server.side.item.dto;

import com.server.side.item.domain.Item;
import lombok.*;

import java.util.ArrayList;

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

    private ArrayList<String> information;

    public Item toEntity() {
        return Item.builder()
                .name(this.name)
                .price(this.price)
                .category(this.category)
                .image(this.image)
                .information(this.information)
                .build();
    }

    public ItemDto toResponse() {
        return ItemDto.builder()
                .name(this.name)
                .price(this.price)
                .category(this.category)
                .image(this.image)
                .information(this.information)
                .build();
    }
}
