package com.vidhan.FarmchainX.service;

import com.vidhan.FarmchainX.entity.Product;
import com.vidhan.FarmchainX.entity.SupplyChainEvent;
import com.vidhan.FarmchainX.entity.User;
import com.vidhan.FarmchainX.repository.SupplyChainEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SupplyChainEventService {

    @Autowired
    private SupplyChainEventRepository supplyChainEventRepository;

    /**
     * Create harvest event when product is created
     */
    @Transactional
    public SupplyChainEvent createHarvestEvent(Product product) {
        SupplyChainEvent event = new SupplyChainEvent();
        event.setProduct(product);
        event.setType("Harvest");
        event.setLocation(product.getFarmer().getAddress());
        event.setTimestamp(
                product.getHarvestDate() != null ? product.getHarvestDate().atStartOfDay() : LocalDateTime.now());
        event.setActor(product.getFarmer().getName());
        event.setActorUser(product.getFarmer());
        event.setDescription(String.format("%s harvested from %s farm. Quality: %s, Quantity: %d %s",
                product.getProductName(),
                product.getFarmer().getName(),
                product.getQuality() != null ? product.getQuality() : "Standard",
                product.getQuantity(),
                product.getUnit()));
        event.setProductCondition("Good");
        event.setTemperature(product.getStorageTemp());

        return supplyChainEventRepository.save(event);
    }

    /**
     * Create quality check event
     */
    @Transactional
    public SupplyChainEvent createQualityCheckEvent(Product product, String inspector, String result) {
        SupplyChainEvent event = new SupplyChainEvent();
        event.setProduct(product);
        event.setType("Quality Check");
        event.setLocation(product.getFarmer().getAddress());
        event.setTimestamp(LocalDateTime.now());
        event.setActor(inspector);
        event.setDescription(String.format("Quality inspection completed. Result: %s. %s",
                result,
                product.getOrganic() ? "Certified Organic." : ""));
        event.setProductCondition(result);
        event.setTemperature(product.getStorageTemp());

        return supplyChainEventRepository.save(event);
    }

    /**
     * Create status change event
     */
    @Transactional
    public SupplyChainEvent createStatusChangeEvent(Product product, String previousStatus, String newStatus) {
        SupplyChainEvent event = new SupplyChainEvent();
        event.setProduct(product);
        event.setType("Status Update");
        event.setLocation(getLocationForStatus(newStatus, product));
        event.setTimestamp(LocalDateTime.now());
        event.setActor(product.getFarmer().getName());
        event.setActorUser(product.getFarmer());
        event.setDescription(String.format("Product status changed from %s to %s",
                previousStatus, newStatus));
        event.setProductCondition("Good");
        event.setTemperature(product.getStorageTemp());

        return supplyChainEventRepository.save(event);
    }

    /**
     * Create transit event
     */
    @Transactional
    public SupplyChainEvent createTransitEvent(Product product, String from, String to, String transporter) {
        SupplyChainEvent event = new SupplyChainEvent();
        event.setProduct(product);
        event.setType("Transit");
        event.setLocation(from + " → " + to);
        event.setTimestamp(LocalDateTime.now());
        event.setActor(transporter);
        event.setDescription(String.format("Product in transit from %s to %s. Transported by %s",
                from, to, transporter));
        event.setProductCondition("Good");
        event.setTemperature(product.getStorageTemp());

        return supplyChainEventRepository.save(event);
    }

    /**
     * Create delivery event
     */
    @Transactional
    public SupplyChainEvent createDeliveryEvent(Product product, String location, String recipient) {
        SupplyChainEvent event = new SupplyChainEvent();
        event.setProduct(product);
        event.setType("Delivery");
        event.setLocation(location);
        event.setTimestamp(LocalDateTime.now());
        event.setActor(recipient);
        event.setDescription(String.format("Product delivered to %s at %s",
                recipient, location));
        event.setProductCondition("Good");

        return supplyChainEventRepository.save(event);
    }

    /**
     * Get location based on status
     */
    private String getLocationForStatus(String status, Product product) {
        return switch (status) {
            case "CULTIVATION" -> product.getFarmer().getAddress();
            case "HARVESTED" -> product.getFarmer().getAddress() + " (Farm Storage)";
            case "IN_TRANSIT" -> "In Transit";
            case "AT_DISTRIBUTOR" -> "Distribution Center";
            case "AT_RETAILER" -> "Retail Store";
            case "DELIVERED" -> "Customer Location";
            case "SOLD" -> "Sold";
            default -> "Unknown Location";
        };
    }
}
