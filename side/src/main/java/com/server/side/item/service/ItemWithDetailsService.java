package com.server.side.item.service;

import com.server.side.item.dto.ItemWithDetailsDTO;
import com.server.side.item.dto.ItemWithDetailsRegistrationRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemWithDetailsService {

    ItemWithDetailsDTO addItemWithDetails(ItemWithDetailsRegistrationRequest request, MultipartFile thumbnail, List<MultipartFile> detailImages);

    List<ItemWithDetailsDTO> findAllItems();
}
