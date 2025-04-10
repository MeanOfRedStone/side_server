package com.server.side.itemDetail.service.impl;

import com.server.side.itemDetail.domain.ItemDetailRepository;
import com.server.side.itemDetail.dto.ItemDetailDTO;
import com.server.side.itemDetail.dto.ItemDetailRegistrationRequest;
import com.server.side.itemDetail.service.ItemDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.server.side.itemDetail.dto.ItemDetailDTO.fromEntity;

@Service
@RequiredArgsConstructor
public class ItemDetailServiceImpl implements ItemDetailService {

    private final ItemDetailRepository repository;

    @Override
    public ItemDetailDTO addItemDetail(ItemDetailRegistrationRequest request) {
        return fromEntity(repository.save(request.toEntity()));
    }
}
