package com.sparta.miniorder.order.repository;

import com.sparta.miniorder.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
