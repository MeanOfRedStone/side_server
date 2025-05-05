package com.server.side.item.service;

import com.server.side.item.dto.ItemDTO;
import com.server.side.item.dto.ItemRegistrationRequest;

public interface ItemService {

    ItemDTO addItem(ItemRegistrationRequest request);
}
