package com.server.side.item.repository;

import com.server.side.item.domain.ItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {

    List<ItemDetail> findAllByItemId(Long itemId);
}
