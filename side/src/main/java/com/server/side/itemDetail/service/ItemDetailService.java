package com.server.side.itemDetail.service;

import com.server.side.itemDetail.dto.ItemDetailDTO;
import com.server.side.itemDetail.dto.ItemDetailRegistrationRequest;

public interface ItemDetailService {

    ItemDetailDTO addItemDetail(ItemDetailRegistrationRequest request);
}
