package com.server.side.item.service;

import com.server.side.item.dto.ItemDto;
import com.server.side.item.dto.ItemRegistrationRequest;

public interface ItemService {

    public ItemDto addItem(ItemRegistrationRequest request);
}
