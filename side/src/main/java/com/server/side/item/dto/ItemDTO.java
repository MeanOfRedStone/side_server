package com.server.side.item.dto;

import com.server.side.item.domain.Item;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemDTO {

    private Long id;

    private String name;

    private Integer price;

    private String category;

    private String description;

    private String image;

    private List<String> information;

    public static ItemWithDetailsDTO fromEntities(Item item) {
        return ItemWithDetailsDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .category(item.getCategory())
                .description(item.getDescription())
                .image(item.getImage())
                .information(item.getInformation())
                .build();
    }

    public static ItemDTO fromEntity(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .category(item.getCategory())
                .description(item.getDescription())
                .image(item.getImage())
                .information(item.getInformation())
                .build();
    }

    public Item toEntity() {
        return Item.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .category(this.category)
                .description(this.description)
                .image(this.image)
                .information(this.information)
                .build();
    }

}
