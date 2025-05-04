package com.server.side.itemdetail.dto;

import com.server.side.item.dto.ItemDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemDetailRegistrationRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldFailWhenOptionIsBlank() {
        ItemDTO item = ItemDTO.builder()
                .id(1L)
                .name("셔츠")
                .price(1000)
                .category("상의")
                .image("셔츠.png")
                .information(List.of("img1", "img2"))
                .build();
        ItemDetailRegistrationRequest request1 = ItemDetailRegistrationRequest.builder()
                .item(item)
                .option(null)
                .quantity(100).build();

        Set<ConstraintViolation<ItemDetailRegistrationRequest>> violations1 = validator.validate(request1);

        assertThat(violations1).anyMatch(v->v.getPropertyPath().toString().equals("option")
        && v.getMessage().equals("{itemDetail.option.notblank}"));

        ItemDetailRegistrationRequest request2 = ItemDetailRegistrationRequest.builder()
                .item(item)
                .option("")
                .quantity(100).build();

        Set<ConstraintViolation<ItemDetailRegistrationRequest>> violations2 = validator.validate(request2);

        assertThat(violations2).anyMatch(v->v.getPropertyPath().toString().equals("option")
                && v.getMessage().equals("{itemDetail.option.notblank}"));

        ItemDetailRegistrationRequest request3 = ItemDetailRegistrationRequest.builder()
                .item(item)
                .option(" ")
                .quantity(100).build();

        Set<ConstraintViolation<ItemDetailRegistrationRequest>> violations3 = validator.validate(request3);

        assertThat(violations3).anyMatch(v->v.getPropertyPath().toString().equals("option")
                && v.getMessage().equals("{itemDetail.option.notblank}"));
    }

    @Test
    void shouldFailWhenQuantityIsNull() {
        ItemDTO item = ItemDTO.builder()
                .id(1L)
                .name("셔츠")
                .price(1000)
                .category("상의")
                .image("셔츠.png")
                .information(List.of("img1", "img2"))
                .build();
        ItemDetailRegistrationRequest request1 = ItemDetailRegistrationRequest.builder()
                .item(item)
                .option("L")
                .quantity(null).build();

        Set<ConstraintViolation<ItemDetailRegistrationRequest>> violations1 = validator.validate(request1);

        assertThat(violations1).anyMatch(v->v.getPropertyPath().toString().equals("quantity")
                && v.getMessage().equals("{itemDetail.quantity.notnull}"));
    }

    @Test
    void shouldFailWhenQuantityLessThanZero() {
        ItemDTO item = ItemDTO.builder()
                .id(1L)
                .name("셔츠")
                .price(1000)
                .category("상의")
                .image("셔츠.png")
                .information(List.of("img1", "img2"))
                .build();
        ItemDetailRegistrationRequest request1 = ItemDetailRegistrationRequest.builder()
                .item(item)
                .option("L")
                .quantity(-1).build();

        Set<ConstraintViolation<ItemDetailRegistrationRequest>> violations1 = validator.validate(request1);

        assertThat(violations1).anyMatch(v->v.getPropertyPath().toString().equals("quantity")
                && v.getMessage().equals("{itemDetail.quantity.min}"));
    }
}
