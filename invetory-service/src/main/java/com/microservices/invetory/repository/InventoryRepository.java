package com.microservices.invetory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.invetory.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, Integer quantity);

}
