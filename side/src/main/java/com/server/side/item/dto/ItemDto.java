package com.server.side.item.dto;

import com.server.side.item.domain.Item;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemDto {

    private long id;

    private String name;

    private int price;

    private String category;

    private String image;

    private String information;

    private String measurement;

    public static ItemDto fromEntity(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .category(item.getCategory())
                .image(item.getImage())
                .information(item.getInformation())
                .measurement(item.getMeasurement())
                .build();
    }

}
