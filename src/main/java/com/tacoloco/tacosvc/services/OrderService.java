package com.tacoloco.tacosvc.services;

import com.tacoloco.tacosvc.dtos.request.OrderRequestDTO;
import com.tacoloco.tacosvc.dtos.request.TacoAndQtyRequestDTO;
import com.tacoloco.tacosvc.dtos.response.OrderResponseDTO;
import com.tacoloco.tacosvc.entities.Customer;
import com.tacoloco.tacosvc.entities.Order;
import com.tacoloco.tacosvc.entities.Taco;
import com.tacoloco.tacosvc.exception.CustomerNotFoundException;
import com.tacoloco.tacosvc.exception.OrderNotFoundException;
import com.tacoloco.tacosvc.exception.TacoNotFoundException;
import com.tacoloco.tacosvc.repositories.CustomerRepository;
import com.tacoloco.tacosvc.repositories.OrderRepository;
import com.tacoloco.tacosvc.repositories.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TacoRepository tacoRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return null;
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public OrderResponseDTO createCustomerOrder(Long customerId, OrderRequestDTO orderRequestDTO) {
        // set order properties
        // customer, tacoList, createTs, lastUpdatedTs,
        Order order = new Order();

        Optional<Customer> customerOptional =
                customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            order.setCustomer(customer);
        } else {
            throw new CustomerNotFoundException("Customer with id " + customerId + " not found");
        }

        List<Taco> tacos = extractTacosFromOrderRequestDTO(orderRequestDTO);
        order.setTacoList(tacos.stream().map(Taco::getId).collect(Collectors.toList()));
        order.setCreateTs(LocalDate.now());
        order.setLastUpdatedTs(LocalDate.now());
        order.setOrderTotal(calculateTotalPrice(tacos));

        Order savedOrder = orderRepository.save(order);
        return buildOrderResponseDTOFromOrder(savedOrder);
    }

    private List<Taco> extractTacosFromOrderRequestDTO(OrderRequestDTO orderRequestDTO) {
        List<Taco> tacos = new ArrayList<>();
        for (TacoAndQtyRequestDTO tacoRequestDTO: orderRequestDTO.getOrderItems()) {
            Optional<Taco> tacoOptional = tacoRepository.findById(tacoRequestDTO.getTacoId());
            if (tacoOptional.isPresent()) {
                Taco taco = tacoOptional.get();
                for(int i = 1; i <= tacoRequestDTO.getQuantity(); i++) {
                    tacos.add(taco);
                }
            } else {
                throw new TacoNotFoundException("Taco with id " + tacoRequestDTO.getTacoId() + " not found");
            }
        }
        return tacos;
    }

    private double calculateTotalPrice(List<Taco> tacos) {
        double orderTotal = 0;
        orderTotal= tacos.stream().map(Taco::getPrice).reduce(Double::sum).get();
        if(tacos.size() >= 4) {
            orderTotal = orderTotal - (orderTotal * 0.2);
        }
        return orderTotal;
    }

    private OrderResponseDTO buildOrderResponseDTOFromOrder(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setCustomerId(order.getCustomer().getId());
        orderResponseDTO.setOrderId(order.getId());
        orderResponseDTO.setOrderTotal(order.getOrderTotal());
        orderResponseDTO.setOrderDate(order.getCreateTs());
        return orderResponseDTO;
    }


    @Override
    public OrderResponseDTO updateOrder(Long customerId, Long orderId, OrderRequestDTO orderRequestDTO) {
        Optional<Customer> customerOptional =
                customerRepository.findById(customerId);
        if(customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("Customer with id " + customerId + " not found");
        }

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(orderOptional.isEmpty()) {
            throw new OrderNotFoundException("Order with id " + orderId + " not found");
        }

        Order existingOrder = orderOptional.get();
        List<Taco> tacos = extractTacosFromOrderRequestDTO(orderRequestDTO);
        existingOrder.setTacoList(tacos.stream().map(Taco::getId).collect(Collectors.toList()));
        existingOrder.setLastUpdatedTs(LocalDate.now());
        existingOrder.setOrderTotal(calculateTotalPrice(tacos));

        Order updatedOrder = orderRepository.save(existingOrder);
        return buildOrderResponseDTOFromOrder(updatedOrder);
    }
}
