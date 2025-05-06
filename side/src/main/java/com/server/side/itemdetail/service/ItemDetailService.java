package com.server.side.itemdetail.service;

import com.server.side.itemdetail.dto.ItemDetailDTO;
import com.server.side.itemdetail.dto.ItemDetailRegistrationRequest;

import java.util.List;

public interface ItemDetailService {

    List<ItemDetailDTO> addAllItemDetails(List<ItemDetailRegistrationRequest> request);
}
