package com.swisscom.demo.productservice.service.impl;

import com.swisscom.demo.productservice.exceptions.BadRequestException;
import com.swisscom.demo.productservice.exceptions.RecordNotFoundException;
import com.swisscom.demo.productservice.model.Product;
import com.swisscom.demo.productservice.model.StockItem;
import com.swisscom.demo.productservice.repository.ProductRepository;
import com.swisscom.demo.productservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long productId) {
        logger.info("Fetching product with id [{}].", productId);
        return productRepository.findById(productId)
                .orElseThrow(() -> new RecordNotFoundException("Product does not exist"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsById(Set<Long> productIds) {
        return productRepository.findAllByIdIn(productIds);
    }

    @Override
    @Transactional
    public Product insertProduct(Product product) {
        StockItem stockItem = new StockItem();
        product.setStockItem(stockItem);
        logger.info("Inserting product [{}]", product);
        try {
            product = productRepository.save(product);
        } catch (Exception e) {
            logger.error("An error occurred while inserting product [{}]. Reason is: [{}]", product, e.getMessage(), e);
            throw new BadRequestException("Unexpected error occurred while inserting product in catalog");
        }
        return product;
    }

    @Override
    public void deleteProduct(Long productId) {
        logger.info("Deleting product with ID [{}].", productId);
        try {
            productRepository.deleteById(productId);
        } catch (Exception e) {
            logger.error("An error occurred while deleting product with ID [{}]. Reason is: [{}]", productId, e.getMessage(), e);
            throw new BadRequestException("Unexpected error occurred while deleting product from catalog");
        }
    }
}