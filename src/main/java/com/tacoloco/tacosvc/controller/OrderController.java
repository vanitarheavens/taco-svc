package com.tacoloco.tacosvc.controller;

import com.tacoloco.tacosvc.dtos.request.OrderRequestDTO;
import com.tacoloco.tacosvc.dtos.response.OrderResponseDTO;
import com.tacoloco.tacosvc.entities.Customer;
import com.tacoloco.tacosvc.entities.Order;
import com.tacoloco.tacosvc.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
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
        Optional<Order> optionalOrder = orderService.getOrderById(orderId);
        return optionalOrder.map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<OrderResponseDTO> createCustomerOrder(
            @PathVariable Long customerId,
            @Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO orderResponseDTO = orderService.createCustomerOrder(customerId, orderRequestDTO);
        return new ResponseEntity<>(orderResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}/{orderId}")
    public ResponseEntity<OrderResponseDTO> updateCustomerOrder(
            @PathVariable Long customerId,
            @PathVariable Long orderId,
            @Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO orderResponseDTO = orderService.updateOrder(customerId, orderId, orderRequestDTO);
        return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
    }


}

