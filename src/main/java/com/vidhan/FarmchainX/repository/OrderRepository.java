package com.vidhan.FarmchainX.repository;

import com.vidhan.FarmchainX.entity.Order;
import com.vidhan.FarmchainX.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @Query("SELECT o FROM Order o WHERE o.consumer.id = :consumerId ORDER BY o.orderDate DESC")
    List<Order> findByConsumerIdOrderByOrderDateDesc(@Param("consumerId") String consumerId);

    List<Order> findByStatus(OrderStatus status);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.consumer.id = :consumerId")
    Long countByConsumerId(@Param("consumerId") String consumerId);
}
