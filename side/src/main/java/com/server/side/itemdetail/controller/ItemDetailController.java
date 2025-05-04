package com.server.side.itemdetail.controller;

import com.server.side.itemdetail.dto.ItemDetailDTO;
import com.server.side.itemdetail.dto.ItemDetailRegistrationRequest;
import com.server.side.itemdetail.service.ItemDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items/{itemId}/details")
public class ItemDetailController {

    private final ItemDetailService service;

    @PostMapping
    public ResponseEntity<ItemDetailDTO> createItemDetail(
            @PathVariable("itemId") Long itemId,
            @RequestBody ItemDetailRegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addItemDetail(request));
    }
}
