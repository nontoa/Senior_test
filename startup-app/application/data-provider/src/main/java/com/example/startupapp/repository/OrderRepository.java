package com.example.startupapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.startupapp.dao.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
