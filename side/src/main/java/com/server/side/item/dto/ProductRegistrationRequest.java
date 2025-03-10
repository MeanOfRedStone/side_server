package com.server.side.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRegistrationRequest {

    private String name;

    private int price;

    private String category;

    private String image;

    private String information;

    private String measurement;

}
