package com.swisscom.demo.productservice.controller;

import com.swisscom.demo.productservice.model.Product;
import com.swisscom.demo.productservice.model.enums.OrderState;
import com.swisscom.demo.productservice.model.requests.OrderRequest;
import com.swisscom.demo.productservice.model.response.OrderedProduct;
import com.swisscom.demo.productservice.model.response.ReservedItemsResponse;
import com.swisscom.demo.productservice.service.ProductService;
import com.swisscom.demo.productservice.service.ReservedStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class ProductController {

    private final ProductService productService;

    private final ReservedStockService reservedStockService;

    public ProductController(ProductService productService, ReservedStockService reservedStockService) {
        this.productService = productService;
        this.reservedStockService = reservedStockService;
    }

    @GetMapping("/product")
    public ResponseEntity<Product> getProduct(@RequestParam Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/insert")
    public ResponseEntity<Product> insertProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.insertProduct(product));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/order")
    public ResponseEntity<ReservedItemsResponse> makeOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(new ReservedItemsResponse(reservedStockService.makeOrder(orderRequest)));
    }

    @PutMapping("/update-stock")
    public ResponseEntity<Void> updateStock(@RequestParam List<Long> reservedStockIds,
                                            @RequestParam OrderState orderState) {
        reservedStockService.stockUpdate(reservedStockIds, orderState);
        return ResponseEntity.noContent().build();
    }
}
