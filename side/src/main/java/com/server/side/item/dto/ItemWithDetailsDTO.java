package com.server.side.item.dto;

import com.server.side.item.domain.Item;
import com.server.side.item.domain.ItemDetail;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ItemWithDetailsDTO {

    private long id;

    private String name;

    private int price;

    private String category;

    private String description;

    private String image;

    private List<String> information;

    private List<ItemDetailDTO> itemDetails;

    public static ItemWithDetailsDTO fromEntities(Item item, List<ItemDetail> itemDetails) {
        List<ItemDetailDTO> itemDetailDTOS = itemDetails.stream()
                .map(itemDetail -> ItemDetailDTO.builder()
                        .option(itemDetail.getOption())
                        .quantity(itemDetail.getQuantity())
                        .build())
                .collect(Collectors.toList());
        return ItemWithDetailsDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .category(item.getCategory())
                .description(item.getDescription())
                .image(item.getImage())
                .information(item.getInformation())
                .itemDetails(itemDetailDTOS)
                .build();
    }

}
