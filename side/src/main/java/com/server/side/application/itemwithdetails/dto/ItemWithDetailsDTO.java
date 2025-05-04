package com.server.side.application.itemwithdetails.dto;

import com.server.side.item.domain.Item;
import com.server.side.item.dto.ItemDTO;
import com.server.side.itemdetail.domain.ItemDetail;
import com.server.side.itemdetail.dto.ItemDetailDTO;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemWithDetailsDTO {

    private Long id;

    private String name;

    private Integer price;

    private String category;

    private String description;

    private String image;

    private List<String> information;

    private List<ItemDetailDTO> itemDetails;

    public static ItemWithDetailsDTO fromEntities(Item item, List<ItemDetail> itemDetails) {
        return ItemWithDetailsDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .category(item.getCategory())
                .description(item.getDescription())
                .image(item.getImage())
                .information(item.getInformation())
                .itemDetails(itemDetails.stream()
                        .map(itemDetail -> ItemDetailDTO.builder()
                                .option(itemDetail.getOption())
                                .quantity(itemDetail.getQuantity())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public ItemDTO toItemDTO() {
        return ItemDTO.builder()
                .id(this.getId())
                .name(this.getName())
                .price(this.getPrice())
                .category(this.getCategory())
                .description(this.getDescription())
                .image(this.getImage())
                .information(this.getInformation())
                .build();
    }
}
