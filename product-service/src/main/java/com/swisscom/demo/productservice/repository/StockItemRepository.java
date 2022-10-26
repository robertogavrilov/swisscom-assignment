package com.swisscom.demo.productservice.repository;

import com.swisscom.demo.productservice.model.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StockItemRepository extends JpaRepository<StockItem, Long> {

    List<StockItem> findAllByIdIn(List<Long> ids);

    Set<StockItem> findAllByReservedStocksIdIn(List<Long> reservedStocks);

}
