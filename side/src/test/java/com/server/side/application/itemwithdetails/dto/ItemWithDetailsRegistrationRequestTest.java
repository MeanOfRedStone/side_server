package com.server.side.application.itemwithdetails.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemWithDetailsRegistrationRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldFailWhenNameIsBlank() {
        ItemWithDetailsRegistrationRequest request1 = ItemWithDetailsRegistrationRequest.builder()
                .name(null)
                .price(1000)
                .category("상의")
                .description("멋진 셔츠").build();

        Set<ConstraintViolation<ItemWithDetailsRegistrationRequest>> violations1 = validator.validate(request1);

        assertThat(violations1).anyMatch(v -> v.getPropertyPath().toString().equals("name")
                && v.getMessage().equals("{item.name.notblank}"));


        ItemWithDetailsRegistrationRequest request2 = ItemWithDetailsRegistrationRequest.builder()
                .name("")
                .price(1000)
                .category("상의")
                .description("멋진 셔츠").build();

        Set<ConstraintViolation<ItemWithDetailsRegistrationRequest>> violations2 = validator.validate(request2);

        assertThat(violations2).anyMatch(v -> v.getPropertyPath().toString().equals("name")
                && v.getMessage().equals("{item.name.notblank}"));

        ItemWithDetailsRegistrationRequest request3 = ItemWithDetailsRegistrationRequest.builder()
                .name(" ")
                .price(1000)
                .category("상의")
                .description("멋진 셔츠").build();

        Set<ConstraintViolation<ItemWithDetailsRegistrationRequest>> violations3 = validator.validate(request3);

        assertThat(violations3).anyMatch(v -> v.getPropertyPath().toString().equals("name")
                && v.getMessage().equals("{item.name.notblank}"));
    }

    @Test
    void shouldFailWhenPriceIsNull() {
        ItemWithDetailsRegistrationRequest request1 = ItemWithDetailsRegistrationRequest.builder()
                .name("셔츠")
                .price(null)
                .category("상의")
                .description("멋진 셔츠").build();

        Set<ConstraintViolation<ItemWithDetailsRegistrationRequest>> violations1 = validator.validate(request1);

        assertThat(violations1).anyMatch(v -> v.getPropertyPath().toString().equals("price")
        && v.getMessage().equals("{item.price.notnull}"));
    }
    @Test
    void shouldFailWhenPriceLessThanZero() {
        ItemWithDetailsRegistrationRequest request1 = ItemWithDetailsRegistrationRequest.builder()
                .name("셔츠")
                .price(-1)
                .category("상의")
                .description("멋진 셔츠").build();

        Set<ConstraintViolation<ItemWithDetailsRegistrationRequest>> violations1 = validator.validate(request1);

        assertThat(violations1).anyMatch(v -> v.getPropertyPath().toString().equals("price")
        && v.getMessage().equals("{item.price.min}"));
    }

    @Test
    void shouldFailWhenCategoryIsBlank() {
        ItemWithDetailsRegistrationRequest request1 = ItemWithDetailsRegistrationRequest.builder()
                .name("셔츠")
                .price(0)
                .category(null)
                .description("멋진 셔츠")
                .build();

        Set<ConstraintViolation<ItemWithDetailsRegistrationRequest>> violations1 = validator.validate(request1);

        assertThat(violations1).anyMatch(v -> v.getPropertyPath().toString().equals("category")
                && v.getMessage().equals("{item.category.notblank}"));

        ItemWithDetailsRegistrationRequest request2 = ItemWithDetailsRegistrationRequest.builder()
                .name("셔츠")
                .price(0)
                .category("")
                .description("멋진 셔츠")
                .build();

        Set<ConstraintViolation<ItemWithDetailsRegistrationRequest>> violations2 = validator.validate(request2);

        assertThat(violations2).anyMatch(v -> v.getPropertyPath().toString().equals("category")
                && v.getMessage().equals("{item.category.notblank}"));

        ItemWithDetailsRegistrationRequest request3 = ItemWithDetailsRegistrationRequest.builder()
                .name("셔츠")
                .price(0)
                .category(" ")
                .description("멋진 셔츠")
                .build();

        Set<ConstraintViolation<ItemWithDetailsRegistrationRequest>> violations3 = validator.validate(request3);

        assertThat(violations3).anyMatch(v -> v.getPropertyPath().toString().equals("category")
                && v.getMessage().equals("{item.category.notblank}"));
    }

    @Test
    void shouldFailWhenDescriptionIsBlank() {
        ItemWithDetailsRegistrationRequest request1 = ItemWithDetailsRegistrationRequest.builder()
                .name("셔츠")
                .price(0)
                .category("상의")
                .description(null)
                .build();

        Set<ConstraintViolation<ItemWithDetailsRegistrationRequest>> violations1 = validator.validate(request1);

        assertThat(violations1).anyMatch(v -> v.getPropertyPath().toString().equals("description")
        && v.getMessage().equals("{item.description.notblank}"));

        ItemWithDetailsRegistrationRequest request2 = ItemWithDetailsRegistrationRequest.builder()
                .name("셔츠")
                .price(0)
                .category("상의")
                .description("")
                .build();

        Set<ConstraintViolation<ItemWithDetailsRegistrationRequest>> violations2 = validator.validate(request2);

        assertThat(violations2).anyMatch(v -> v.getPropertyPath().toString().equals("description")
                && v.getMessage().equals("{item.description.notblank}"));

        ItemWithDetailsRegistrationRequest request3 = ItemWithDetailsRegistrationRequest.builder()
                .name("셔츠")
                .price(0)
                .category("상의")
                .description(" ")
                .build();

        Set<ConstraintViolation<ItemWithDetailsRegistrationRequest>> violations3 = validator.validate(request3);

        assertThat(violations3).anyMatch(v -> v.getPropertyPath().toString().equals("description")
                && v.getMessage().equals("{item.description.notblank}"));
    }

    @Test
    void shouldFailWhenItemDetailsAreNull() {
        ItemWithDetailsRegistrationRequest request1 = ItemWithDetailsRegistrationRequest.builder()
                .name("셔츠")
                .price(0)
                .category("상의")
                .description("멋진 셔츠")
                .itemDetails(null)
                .build();

        Set<ConstraintViolation<ItemWithDetailsRegistrationRequest>> violations1 = validator.validate(request1);

        assertThat(violations1).anyMatch(v -> v.getPropertyPath().toString().equals("itemDetails")
        && v.getMessage().equals("{item.itemDetails.notnull}"));
    }
}
