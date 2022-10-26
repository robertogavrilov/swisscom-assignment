package com.swisscom.demo.productservice.service.impl;

import com.swisscom.demo.productservice.model.Product;
import com.swisscom.demo.productservice.model.StockItem;
import com.swisscom.demo.productservice.repository.StockItemRepository;
import com.swisscom.demo.productservice.service.ProductService;
import com.swisscom.demo.productservice.service.StockItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class StockItemServiceImpl implements StockItemService {

    private final StockItemRepository stockItemRepository;

    private final ProductService productService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public StockItemServiceImpl(StockItemRepository stockItemRepository, ProductService productService) {
        this.stockItemRepository = stockItemRepository;
        this.productService = productService;
    }

    @Override
    public StockItem getStockInformation(Long productId) {
        logger.info("Fetching stock information by product ID [{}].", productId);
        Product product = productService.getProductById(productId);
        return product.getStockItem();
    }

    @Override
    public StockItem increaseStock(Long productId, int quantity) {
        logger.info("Increasing stock for product with ID [{}} for [{}].", productId, quantity);
        Product product = productService.getProductById(productId);
        StockItem stockItem = product.getStockItem();
        stockItem.increaseStockItems(quantity);
        return stockItemRepository.save(stockItem);
    }

    @Override
    public List<StockItem> saveMultiple(List<StockItem> stockItems) {
        logger.info("Updating multiple stock items [{}].", stockItems);
        return stockItemRepository.saveAll(stockItems);
    }

    @Override
    public Set<StockItem> findAllByReservedStockItemIds(List<Long> reservedStockIds) {
        return stockItemRepository.findAllByReservedStocksIdIn(reservedStockIds);
    }
}