package com.server.side.itemdetail.service.impl;

import com.server.side.itemdetail.repository.ItemDetailRepository;
import com.server.side.itemdetail.dto.ItemDetailDTO;
import com.server.side.itemdetail.dto.ItemDetailRegistrationRequest;
import com.server.side.itemdetail.service.ItemDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.server.side.itemdetail.dto.ItemDetailDTO.fromEntity;

@Service
@RequiredArgsConstructor
public class ItemDetailServiceImpl implements ItemDetailService {

    private final ItemDetailRepository repository;

    @Override
    public ItemDetailDTO addItemDetail(ItemDetailRegistrationRequest request) {
        return fromEntity(repository.save(request.toEntity()));
    }
}
