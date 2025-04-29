package com.server.side.item.service.impl;

import com.server.side.item.repository.ItemDetailRepository;
import com.server.side.item.dto.ItemDetailDTO;
import com.server.side.item.dto.ItemDetailRegistrationRequest;
import com.server.side.item.service.ItemDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.server.side.item.dto.ItemDetailDTO.fromEntity;

@Service
@RequiredArgsConstructor
public class ItemDetailServiceImpl implements ItemDetailService {

    private final ItemDetailRepository repository;

    @Override
    public ItemDetailDTO addItemDetail(ItemDetailRegistrationRequest request) {
        return fromEntity(repository.save(request.toEntity()));
    }
}
