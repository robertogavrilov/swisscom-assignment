package com.swisscom.demo.productservice.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sw_stock_item")
public class StockItem {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<ReservedStock> reservedStocks;

    @Column
    private int availableItems = 1;

    @Version
    private Long version;

    public StockItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public List<ReservedStock> getReservedStocks() {
        return reservedStocks;
    }

    public void setReservedStocks(List<ReservedStock> reservedStocks) {
        this.reservedStocks = reservedStocks;
    }

    public Long getVersion() {
        return version;
    }

    public void increaseStockItems(int quantity) {
        this.availableItems += quantity;
    }

    public void decreaseStockItems(int quantity) {
        this.availableItems -= quantity;
    }

    @Override
    public String toString() {
        return "StockItem{" +
                "id=" + id +
                ", availableItems=" + availableItems +
                ", version=" + version +
                '}';
    }
}
