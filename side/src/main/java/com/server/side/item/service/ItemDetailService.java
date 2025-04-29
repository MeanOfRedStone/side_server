package com.server.side.item.service;

import com.server.side.item.dto.ItemDetailDTO;
import com.server.side.item.dto.ItemDetailRegistrationRequest;

public interface ItemDetailService {

    ItemDetailDTO addItemDetail(ItemDetailRegistrationRequest request);
}
