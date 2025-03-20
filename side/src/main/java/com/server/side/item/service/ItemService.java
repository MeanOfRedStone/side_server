package com.server.side.item.service;

import com.server.side.item.domain.Item;
import com.server.side.item.dto.ItemRegistrationRequest;

public interface ItemService {

    Item addItem(ItemRegistrationRequest request);
}
