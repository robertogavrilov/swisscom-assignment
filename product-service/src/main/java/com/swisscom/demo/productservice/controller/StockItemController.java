package com.swisscom.demo.productservice.controller;

import com.swisscom.demo.productservice.model.StockItem;
import com.swisscom.demo.productservice.model.requests.IncreaseStockItemRequest;
import com.swisscom.demo.productservice.service.StockItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
public class StockItemController {

    private final StockItemService stockItemService;

    public StockItemController(StockItemService stockItemService) {
        this.stockItemService = stockItemService;
    }

    @GetMapping("/info")
    public ResponseEntity<StockItem> getStockInfo(@RequestParam Long productId) {
        return ResponseEntity.ok(stockItemService.getStockInformation(productId));
    }

    @PutMapping("/increase")
    public ResponseEntity<StockItem> increaseStockItems(@RequestBody IncreaseStockItemRequest increaseStockItemRequest) {
        return ResponseEntity.ok(stockItemService.increaseStock(increaseStockItemRequest.getProductId(), increaseStockItemRequest.getQuantity()));
    }
}