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

        assertThat(violations1).anyMatch(v -> v.getPropertyPath().toString().equals("name"));

        ItemRegistrationRequest request2 = ItemRegistrationRequest.builder()
                .name("")
                .price(1000)
                .category("상의").build();

        Set<ConstraintViolation<ItemRegistrationRequest>> violations2 = validator.validate(request2);

        assertThat(violations2).anyMatch(v -> v.getPropertyPath().toString().equals("name"));

        ItemRegistrationRequest request3 = ItemRegistrationRequest.builder()
                .name(" ")
                .price(1000)
                .category("상의").build();

        Set<ConstraintViolation<ItemRegistrationRequest>> violations3 = validator.validate(request3);

        assertThat(violations3).anyMatch(v -> v.getPropertyPath().toString().equals("name"));
    }
}
