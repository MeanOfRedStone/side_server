package com.server.side.item.dto;

import com.server.side.item.domain.Item;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemRegistrationRequest {

    private Long id;
    @NotBlank(message = "{item.name.notblank}")
    private String name;
    @NotNull(message = "{item.price.notnull}")
    @Min(value = 0, message = "{item.price.min}")
    private Integer price;
    @NotBlank(message = "{item.category.notblank}")
    private String category;
    @NotBlank(message = "{item.description.notblank}")
    private String description;
    @NotBlank(message = "{item.image.notblank}")
    private String image;
    @NotNull(message = "{item.information.notnull}")
    private List<String> information;

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
