package com.server.side.item.controller;

import com.server.side.item.dto.ItemDto;
import com.server.side.item.dto.ItemRegistrationRequest;
import com.server.side.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemRegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addItem(request));
    }
}
