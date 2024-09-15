package com.microservices.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
