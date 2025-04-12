package com.server.side.item.service;

import com.server.side.item.dto.ItemDto;
import com.server.side.item.dto.ItemRegistrationRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    ItemDto addItem(ItemRegistrationRequest request, MultipartFile thumbnail,List<MultipartFile> detailImages);

    List<ItemDto> findAllItems();
}
