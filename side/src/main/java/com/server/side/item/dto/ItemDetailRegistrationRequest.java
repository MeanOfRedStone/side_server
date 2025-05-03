package com.server.side.item.dto;

import com.server.side.item.domain.ItemDetail;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.server.side.item.dto.ItemDTO.fromEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemDetailRegistrationRequest {

    private Long id;

    private ItemDTO item;
    @NotBlank(message = "{itemDetail.option.notblank}")
    private String option;
    @NotNull(message = "{itemDetail.quantity.notnull}")
    @Min(value = 0, message = "{itemDetail.quantity.min}")
    private Integer quantity;

    public ItemDetail toEntity() {
        return ItemDetail.builder()
                .id(this.id)
                .item(this.item.toEntity())
                .option(this.option)
                .quantity(this.quantity)
                .build();
    }

    public ItemDetailDTO toResponse() {
        return ItemDetailDTO.builder()
                .id(this.id)
                .item(fromEntity(this.item.toEntity()))
                .option(this.option)
                .quantity(this.quantity)
                .build();
    }
}
