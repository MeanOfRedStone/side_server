package com.server.side.item.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemRegistrationRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldFailWhenNameIsBlank() {
        ItemRegistrationRequest request1 = ItemRegistrationRequest.builder()
                .name(null)
                .price(1000)
                .category("상의").build();

        Set<ConstraintViolation<ItemRegistrationRequest>> violations1 = validator.validate(request1);

        assertThat(violations1).anyMatch(v -> v.getPropertyPath().toString().equals("name")
                && v.getMessage().equals("{item.name.notblank}"));


        ItemRegistrationRequest request2 = ItemRegistrationRequest.builder()
                .name("")
                .price(1000)
                .category("상의").build();

        Set<ConstraintViolation<ItemRegistrationRequest>> violations2 = validator.validate(request2);

        assertThat(violations2).anyMatch(v -> v.getPropertyPath().toString().equals("name")
                && v.getMessage().equals("{item.name.notblank}"));

        ItemRegistrationRequest request3 = ItemRegistrationRequest.builder()
                .name(" ")
                .price(1000)
                .category("상의").build();

        Set<ConstraintViolation<ItemRegistrationRequest>> violations3 = validator.validate(request3);

        assertThat(violations3).anyMatch(v -> v.getPropertyPath().toString().equals("name")
                && v.getMessage().equals("{item.name.notblank}"));
    }

    @Test
    void shouldFailWhenLessThanZero() {
        ItemRegistrationRequest request1 = ItemRegistrationRequest.builder()
                .name("셔츠")
                .price(-1)
                .category("상의").build();

        Set<ConstraintViolation<ItemRegistrationRequest>> violations1 = validator.validate(request1);

        assertThat(violations1).anyMatch(v -> v.getPropertyPath().toString().equals("price")
        && v.getMessage().equals("{item.price.min}"));
    }

    @Test
    void shouldFailWhenCategoryIsBlank() {
        ItemRegistrationRequest request1 = ItemRegistrationRequest.builder()
                .name("셔츠")
                .price(0)
                .category(null)
                .build();

        Set<ConstraintViolation<ItemRegistrationRequest>> violations1 = validator.validate(request1);

        assertThat(violations1).anyMatch(v -> v.getPropertyPath().toString().equals("category")
                && v.getMessage().equals("{item.category.notblank}"));

        ItemRegistrationRequest request2 = ItemRegistrationRequest.builder()
                .name("셔츠")
                .price(0)
                .category("")
                .build();

        Set<ConstraintViolation<ItemRegistrationRequest>> violations2 = validator.validate(request2);

        assertThat(violations2).anyMatch(v -> v.getPropertyPath().toString().equals("category")
                && v.getMessage().equals("{item.category.notblank}"));

        ItemRegistrationRequest request3 = ItemRegistrationRequest.builder()
                .name("셔츠")
                .price(0)
                .category(" ")
                .build();

        Set<ConstraintViolation<ItemRegistrationRequest>> violations3 = validator.validate(request3);

        assertThat(violations3).anyMatch(v -> v.getPropertyPath().toString().equals("category")
                && v.getMessage().equals("{item.category.notblank}"));
    }
}
