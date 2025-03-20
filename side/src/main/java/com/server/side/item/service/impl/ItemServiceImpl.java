package com.server.side.item.service.impl;

import com.server.side.item.domain.ItemRepository;
import com.server.side.item.dto.ItemDto;
import com.server.side.item.dto.ItemRegistrationRequest;
import com.server.side.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.server.side.item.dto.ItemDto.fromEntity;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    @Override
    public ItemDto addItem(ItemRegistrationRequest request) {
        return fromEntity(repository.save(request.toEntity()));
    }
}
