package com.server.side.item.service.impl;

import com.server.side.item.dto.ItemDTO;
import com.server.side.item.dto.ItemRegistrationRequest;
import com.server.side.item.repository.ItemRepository;
import com.server.side.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    @Override
    public ItemDTO addItem(ItemRegistrationRequest request) {
        return ItemDTO.fromEntity(repository.save(request.toEntity()));
    }
}
