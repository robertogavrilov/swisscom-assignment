package com.swisscom.demo.productservice.service;

import com.swisscom.demo.productservice.model.StockItem;

import java.util.List;
import java.util.Set;

public interface StockItemService {

    StockItem getStockInformation(Long productId);

    StockItem increaseStock(Long productId, int quantity);

    List<StockItem> saveMultiple(List<StockItem> stockItems);

    Set<StockItem> findAllByReservedStockItemIds(List<Long> reservedStocks);
}
