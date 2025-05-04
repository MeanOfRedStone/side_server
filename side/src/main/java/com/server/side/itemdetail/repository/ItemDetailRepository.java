package com.server.side.itemdetail.repository;

import com.server.side.itemdetail.domain.ItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {

    List<ItemDetail> findAllByItemId(Long itemId);
}
