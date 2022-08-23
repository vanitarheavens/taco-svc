package com.tacoloco.tacosvc.services;

import com.tacoloco.tacosvc.dtos.request.OrderRequestDTO;
import com.tacoloco.tacosvc.dtos.response.OrderResponseDTO;
import com.tacoloco.tacosvc.entities.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderService {

    List<Order> getAllOrders();
    Optional<Order> getOrderById(Long id);
    OrderResponseDTO createCustomerOrder(Long customerId, OrderRequestDTO orderRequest);
    OrderResponseDTO updateOrder(Long customerId, Long orderId, OrderRequestDTO orderRequestDTO);
}
