package com.server.side.item.service.impl;

import com.server.side.item.domain.Item;
import com.server.side.item.domain.ItemRepository;
import com.server.side.item.dto.ItemRegistrationRequest;
import com.server.side.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    @Override
    public Item addItem(ItemRegistrationRequest request) {
        return repository.save(request.toEntity());
    }
}
