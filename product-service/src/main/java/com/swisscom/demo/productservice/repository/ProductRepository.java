package com.swisscom.demo.productservice.repository;

import com.swisscom.demo.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByIdIn(Set<Long> productsId);

    List<Product> findAllByStockItemAvailableItemsGreaterThan(int quantity);
}
