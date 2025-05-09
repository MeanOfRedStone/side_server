package com.server.side.item.service;

import com.server.side.item.dto.ItemDTO;
import com.server.side.item.dto.ItemRegistrationRequest;

import java.util.List;

public interface ItemService {

    ItemDTO addItem(ItemRegistrationRequest request, String image, List<String> information);

    List<ItemDTO> searchAllItems();
}
