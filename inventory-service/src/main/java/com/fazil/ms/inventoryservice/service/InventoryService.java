package com.fazil.ms.inventoryservice.service;

import com.fazil.ms.inventoryservice.entity.Inventory;
import com.fazil.ms.inventoryservice.mapper.InventoryMapper;
import com.fazil.ms.inventoryservice.repository.InventoryRepository;
import com.fazil.ms.inventoryservice.resource.InventoryRequest;
import com.fazil.ms.inventoryservice.resource.InventoryResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findBySkuCode(inventoryRequest.skuCode());
        Inventory inventory = null;
        if (inventoryOptional.isPresent()) {
            inventory = inventoryOptional.get();
            inventory.setQuantity(inventoryRequest.quantity());
        } else {
            inventory = InventoryMapper.convert(inventoryRequest);
        }
        inventoryRepository.save(inventory);
        return InventoryMapper.convert(inventory);
    }

    public List<InventoryResponse> getInventoriesBySkuCodes(List<String> skuCodes) {
        Optional<List<Inventory>> optionalInventoryResponseList =  inventoryRepository.findBySkuCodeIn(skuCodes);
        if (optionalInventoryResponseList.isPresent()) {
            return optionalInventoryResponseList.get().stream()
                    .map(InventoryMapper::convert)
                    .collect(Collectors.toUnmodifiableList());
        } else {
            throw new RuntimeException("No skuCodes are present int the system");
        }
    }
}
