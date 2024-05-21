package com.fazil.ms.inventoryservice.repository;

import com.fazil.ms.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkuCode(String skuCode);

    Optional<List<Inventory>> findBySkuCodeIn(List<String> skuCodes);
}
