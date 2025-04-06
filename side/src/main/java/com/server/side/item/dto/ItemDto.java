package com.server.side.item.dto;

import com.server.side.item.domain.Item;
import lombok.*;

import java.util.List;

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

    private List<String> information;

    public static ItemDto fromEntity(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .category(item.getCategory())
                .image(item.getImage())
                .information(item.getInformation())
                .build();
    }

}
