package com.tacoloco.tacosvc.repositories;

import com.tacoloco.tacosvc.entities.Customer;
import com.tacoloco.tacosvc.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomer(Customer customer);
}
