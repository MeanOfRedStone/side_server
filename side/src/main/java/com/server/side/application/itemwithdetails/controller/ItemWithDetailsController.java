package com.server.side.application.itemwithdetails.controller;

import com.server.side.application.itemwithdetails.dto.ItemWithDetailsDTO;
import com.server.side.application.itemwithdetails.dto.ItemWithDetailsRegistrationRequest;
import com.server.side.application.itemwithdetails.service.ItemWithDetailsService;
import jakarta.validation.Valid;
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
public class ItemWithDetailsController {

    private final ItemWithDetailsService service;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ItemWithDetailsDTO> createItem(
            @RequestPart("request") @Valid ItemWithDetailsRegistrationRequest request,
            @RequestPart("thumbnailImage") MultipartFile thumbnailImage,
            @RequestPart("detailImages") List<MultipartFile> detailImages
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addItemWithDetails(request, thumbnailImage, detailImages));
    }

    @GetMapping
    public ResponseEntity<List<ItemWithDetailsDTO>> itemList() {
        return ResponseEntity.ok(service.findAllItemsWithDetails());
    }
}
