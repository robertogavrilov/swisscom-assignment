package com.swisscom.demo.productservice.repository;

import com.swisscom.demo.productservice.model.ReservedStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservedStockRepository extends JpaRepository<ReservedStock, Long> {
}
