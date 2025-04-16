package com.server.side.item.dto;

import com.server.side.item.domain.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemRegistrationRequest {

    @NotBlank(message = "{item.name.notblank}")
    private String name;

    private int price;

    private String category;

    private String image;

    private List<String> information;

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
