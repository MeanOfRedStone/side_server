package com.server.side.itemdetail.dto;

import com.server.side.item.dto.ItemDTO;
import com.server.side.itemdetail.domain.ItemDetail;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemDetailDTO {

    private Long id;

    private ItemDTO item;

    private String option;

    private Integer quantity;

    public static ItemDetailDTO fromEntity(ItemDetail itemDetail) {
        return ItemDetailDTO.builder()
                .id(itemDetail.getId())
                .item(ItemDTO.fromEntity(itemDetail.getItem()))
                .option(itemDetail.getOption())
                .quantity(itemDetail.getQuantity())
                .build();
    }
}
