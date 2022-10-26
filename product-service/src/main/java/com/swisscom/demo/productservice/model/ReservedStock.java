package com.swisscom.demo.productservice.model;

import javax.persistence.*;

@Entity
@Table(name = "sw_reserved_stock")
public class ReservedStock {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "ReservedStock{" +
                "id=" + id +
                ", version=" + version +
                '}';
    }
}
