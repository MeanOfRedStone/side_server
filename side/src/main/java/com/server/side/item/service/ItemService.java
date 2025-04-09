package com.server.side.item.service;

import com.server.side.item.dto.ItemDto;
import com.server.side.item.dto.ItemRegistrationRequest;

import java.util.List;

public interface ItemService {

    ItemDto addItem(ItemRegistrationRequest request);

    List<ItemDto> findAllItems();
}
