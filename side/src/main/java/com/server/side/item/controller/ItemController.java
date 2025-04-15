package com.server.side.item.controller;

import com.server.side.item.dto.ItemDto;
import com.server.side.item.dto.ItemRegistrationRequest;
import com.server.side.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ItemDto> createItem(
            @RequestPart("request") ItemRegistrationRequest request,
            @RequestPart("thumbnailImage") MultipartFile thumbnailImage,
            @RequestPart("detailImages") List<MultipartFile> detailImages
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addItem(request, thumbnailImage, detailImages));
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> itemList() {
        return ResponseEntity.ok(service.findAllItems());
    }
}
