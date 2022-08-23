package com.tacoloco.tacosvc.controller;

import com.tacoloco.tacosvc.dtos.request.OrderRequestDTO;
import com.tacoloco.tacosvc.dtos.response.OrderResponseDTO;
import com.tacoloco.tacosvc.entities.Customer;
import com.tacoloco.tacosvc.entities.Order;
import com.tacoloco.tacosvc.services.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@Slf4j(topic = "OrderController")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders =  orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @PathVariable Long orderId
    ) {
        log.info("Getting order by id: {}", orderId);
        Optional<Order> optionalOrder = orderService.getOrderById(orderId);
        return optionalOrder.map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<OrderResponseDTO> createCustomerOrder(
            @PathVariable Long customerId,
            @Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        log.info("Creating order for customer with customerId: {} and order details: {}",
                customerId, orderRequestDTO);
        OrderResponseDTO orderResponseDTO = orderService.createCustomerOrder(customerId, orderRequestDTO);
        return new ResponseEntity<>(orderResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}/customer-orders/{orderId}")
    public ResponseEntity<OrderResponseDTO> updateCustomerOrder(
            @PathVariable Long customerId,
            @PathVariable Long orderId,
            @Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        log.info("Updating order for customer with customerId: {} orderId: {} and order details: {}",
                customerId, orderId, orderRequestDTO);
        OrderResponseDTO orderResponseDTO = orderService.updateOrder(customerId, orderId, orderRequestDTO);
        return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
    }


}

