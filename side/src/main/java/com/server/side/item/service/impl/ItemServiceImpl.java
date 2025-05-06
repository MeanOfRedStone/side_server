package com.server.side.item.service.impl;

import com.server.side.item.domain.Item;
import com.server.side.item.dto.ItemDTO;
import com.server.side.item.dto.ItemRegistrationRequest;
import com.server.side.item.repository.ItemRepository;
import com.server.side.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    @Override
    public ItemDTO addItem(ItemRegistrationRequest request) {
        return ItemDTO.fromEntity(repository.save(request.toEntity()));
    }

    @Override
    public List<ItemDTO> searchAllItems() {
        List<Item> items = repository.findAll();
        if(items == null) {
            return Collections.emptyList();
        }

        return items.stream()
                .map(item -> ItemDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .price(item.getPrice())
                        .category(item.getCategory())
                        .description(item.getDescription())
                        .image(item.getImage())
                        .information(item.getInformation())
                        .build())
                .toList();
    }
}
