package com.server.side.itemDetail.controller;

import com.server.side.itemDetail.dto.ItemDetailDTO;
import com.server.side.itemDetail.dto.ItemDetailRegistrationRequest;
import com.server.side.itemDetail.service.ItemDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.server.side.itemDetail.dto.ItemDetailDTO.fromEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemDetailController {

    private final ItemDetailService service;

    @PostMapping
    public ResponseEntity<ItemDetailDTO> createItemDetail(@RequestBody ItemDetailRegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fromEntity(service.addItemDetail(request)));
    }
}
