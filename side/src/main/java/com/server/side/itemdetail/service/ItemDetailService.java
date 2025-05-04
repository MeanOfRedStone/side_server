package com.server.side.itemdetail.service;

import com.server.side.itemdetail.dto.ItemDetailDTO;
import com.server.side.itemdetail.dto.ItemDetailRegistrationRequest;

public interface ItemDetailService {

    ItemDetailDTO addItemDetail(ItemDetailRegistrationRequest request);
}
