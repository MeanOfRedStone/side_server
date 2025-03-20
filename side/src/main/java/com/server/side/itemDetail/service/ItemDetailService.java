package com.server.side.itemDetail.service;

import com.server.side.itemDetail.domain.ItemDetail;
import com.server.side.itemDetail.dto.ItemDetailRegistrationRequest;

public interface ItemDetailService {

    ItemDetail addItemDetail(ItemDetailRegistrationRequest request);
}
