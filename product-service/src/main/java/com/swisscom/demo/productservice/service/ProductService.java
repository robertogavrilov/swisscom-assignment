package com.swisscom.demo.productservice.service;

import com.swisscom.demo.productservice.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {

    Product getProductById(Long productId);

    List<Product> getAllProducts();

    Product insertProduct(Product product);

    void deleteProduct(Long productId);

    List<Product> getAllProductsById(Set<Long> productIds);
}
