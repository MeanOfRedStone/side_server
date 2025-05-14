package com.server.side.application.itemwithdetails.dto;

import com.server.side.item.domain.Item;
import com.server.side.item.dto.ItemDTO;
import com.server.side.item.dto.ItemRegistrationRequest;
import com.server.side.itemdetail.domain.ItemDetail;
import com.server.side.itemdetail.dto.ItemDetailRegistrationRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemWithDetailsRegistrationRequest {

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
    @NotNull(message = "{item.itemDetails.notnull}")
    private List<ItemDetailRegistrationRequest> itemDetails;

    public Item toItemEntity() {
        return Item.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .category(this.category)
                .description(this.description)
                .build();
    }

    public List<ItemDetail> toItemDetailEntity() {
        return itemDetails.stream()
                .map(itemDetail -> ItemDetail.builder()
                        .item(toItemEntity())
                        .option(itemDetail.getOption())
                        .quantity(itemDetail.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    public ItemRegistrationRequest toItemRegistrationRequest() {
        return ItemRegistrationRequest.builder()
                .id(this.id)
                .name(this.name)
                .price(this.price)
                .category(this.category)
                .description(this.description)
                .build();
    }

    public List<ItemDetailRegistrationRequest> toItemDetailsRegistrationRequest(ItemDTO itemDTO) {
        return itemDetails.stream()
                .map(itemDetail -> ItemDetailRegistrationRequest.builder()
                        .item(itemDTO)
                        .option(itemDetail.getOption())
                        .quantity(itemDetail.getQuantity())
                        .build())
                .toList();
    }

}
