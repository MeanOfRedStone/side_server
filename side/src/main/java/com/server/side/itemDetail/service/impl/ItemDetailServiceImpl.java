package com.server.side.itemDetail.service.impl;

import com.server.side.itemDetail.domain.ItemDetail;
import com.server.side.itemDetail.domain.ItemDetailRepository;
import com.server.side.itemDetail.dto.ItemDetailRegistrationRequest;
import com.server.side.itemDetail.service.ItemDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemDetailServiceImpl implements ItemDetailService {

    private final ItemDetailRepository repository;

    @Override
    public ItemDetail addItemDetail(ItemDetailRegistrationRequest request) {
        return repository.save(request.toEntity());
    }
}
