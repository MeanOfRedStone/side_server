package com.server.side.itemdetail.service.impl;

import com.server.side.item.dto.ItemDTO;
import com.server.side.itemdetail.domain.ItemDetail;
import com.server.side.itemdetail.dto.ItemDetailDTO;
import com.server.side.itemdetail.dto.ItemDetailRegistrationRequest;
import com.server.side.itemdetail.repository.ItemDetailRepository;
import com.server.side.itemdetail.service.ItemDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemDetailServiceImpl implements ItemDetailService {

    private final ItemDetailRepository repository;

    @Override
    public List<ItemDetailDTO> addAllItemDetails(List<ItemDetailRegistrationRequest> request) {
        List<ItemDetail> itemDetails = request.stream()
                .map(itemDetailRegistrationRequest -> ItemDetail.builder()
                        .item(itemDetailRegistrationRequest.getItem().toEntity())
                        .option(itemDetailRegistrationRequest.getOption())
                        .quantity(itemDetailRegistrationRequest.getQuantity()).build())
                .toList();

        return repository.saveAll(itemDetails).stream()
                .map(itemDetail -> ItemDetailDTO.builder()
                        .id(itemDetail.getId())
                        .item(ItemDTO.fromEntity(itemDetail.getItem()))
                        .option(itemDetail.getOption())
                        .quantity(itemDetail.getQuantity())
                        .build())
                .toList();
    }
}
