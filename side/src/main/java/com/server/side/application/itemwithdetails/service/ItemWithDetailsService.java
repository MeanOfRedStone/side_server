package com.server.side.application.itemwithdetails.service;

import com.server.side.application.itemwithdetails.dto.ItemWithDetailsDTO;
import com.server.side.application.itemwithdetails.dto.ItemWithDetailsRegistrationRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemWithDetailsService {

    ItemWithDetailsDTO addItemWithDetails(ItemWithDetailsRegistrationRequest request, MultipartFile thumbnail, List<MultipartFile> detailImages);

    List<ItemWithDetailsDTO> findAllItemsWithDetails();
}
