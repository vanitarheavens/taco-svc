package com.tacoloco.tacosvc.services;

import com.tacoloco.tacosvc.dtos.request.OrderRequestDTO;
import com.tacoloco.tacosvc.dtos.request.TacoAndQtyRequestDTO;
import com.tacoloco.tacosvc.dtos.response.OrderResponseDTO;
import com.tacoloco.tacosvc.entities.Customer;
import com.tacoloco.tacosvc.entities.Order;
import com.tacoloco.tacosvc.entities.Taco;
import com.tacoloco.tacosvc.exception.CustomerNotFoundException;
import com.tacoloco.tacosvc.exception.NotValidInputException;
import com.tacoloco.tacosvc.exception.OrderNotFoundException;
import com.tacoloco.tacosvc.exception.TacoNotFoundException;
import com.tacoloco.tacosvc.repositories.CustomerRepository;
import com.tacoloco.tacosvc.repositories.OrderRepository;
import com.tacoloco.tacosvc.repositories.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j(topic ="OrderService")
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TacoRepository tacoRepository;

    /**
     * Method to get all Orders in customer_orders table
     *
     * @return List<Order> list of order
     */
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Method to create and save an order for a customer from OrderRequestDTO
     *
     * @param customerId Long Id of customer who placed the order
     * @param orderRequestDTO OrderRequestDTO the details of the order
     * @return OrderResponseDTO object which has a few of details of the order
     */
    @Override
    public OrderResponseDTO createCustomerOrder(Long customerId, OrderRequestDTO orderRequestDTO) {
        // set order properties
        // customer, tacoList, createDate, lastUpdatedDate, orderTotal,
        Order order = new Order();

        Optional<Customer> customerOptional =
                customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            // set customer property
            Customer customer = customerOptional.get();
            order.setCustomer(customer);
        } else {
            throw new CustomerNotFoundException("Customer with id " + customerId + " not found");
        }

        // set tacoList property
        List<Taco> tacos = extractTacosFromOrderRequestDTO(orderRequestDTO);
        List<Long> ids = tacos.stream().map(Taco::getId).collect(Collectors.toList());
        order.setTacoList(ids);

        // set createDate and  lastUpdatedDate properties
        order.setCreateDate(LocalDate.now());
        order.setLastUpdatedDate(LocalDate.now());

        // set orderTotal property
        order.setOrderTotal(calculateTotalPrice(tacos));

        Order savedOrder = orderRepository.save(order);
        return buildOrderResponseDTOFromOrder(savedOrder);
    }

    /**
     * Method to extract individual Tacos from the OrderRequestDTO
     * Iterates through each TacoAndQtyRequestDTO and adds Taco items to tacoList depending
     * on the quantity specified. If orderRequestDTO has tacoId =1 and quantity = 4,
     * the Taco with ID 1 will be added to the List 4 times.
     *
     * @param orderRequestDTO OrderRequestDTO which is request body from the client
     * @return List<Taco> list of tacos ie menu items on the order
     */
    private List<Taco> extractTacosFromOrderRequestDTO(OrderRequestDTO orderRequestDTO) {
        List<Taco> tacos = new ArrayList<>();
        for (TacoAndQtyRequestDTO tacoRequestDTO: orderRequestDTO.getOrderItems()) {
            Optional<Taco> tacoOptional = tacoRepository.findById(tacoRequestDTO.getTacoId());
            int quantity = tacoRequestDTO.getQuantity();
            if(quantity <  1) {
                throw new NotValidInputException("Quantity for Taco is less than 1");
            }
            if (tacoOptional.isPresent()) {
                Taco taco = tacoOptional.get();
                for(int i = 1; i <= tacoRequestDTO.getQuantity(); i++) {
                    tacos.add(taco);
                }
            } else {
                throw new TacoNotFoundException("Taco with id " + tacoRequestDTO.getTacoId() + " not found");
            }
        }
        log.info("Successfully extracted Tacos from OrderRequestDTO: {}", orderRequestDTO);
        return tacos;
    }

    /**
     * Method to calculate the total price of the Order from the menu items (Tacos)
     * If number of Tacos is eqaul to or greater than 4, a 20% discount is applied
     *
     * @param tacos List<Taco> list of Tacos which are on the Order request
     * @return double the total cost of all menu items on the order
     */
    private double calculateTotalPrice(List<Taco> tacos) {
        double orderTotal = 0.0;
        if(tacos.size() == 0)
            return orderTotal;

        orderTotal = tacos.stream().map(Taco::getPrice).reduce(Double::sum).get();
        if(tacos.size() >= 4) {
            orderTotal = orderTotal - (orderTotal * 0.2);
        }
        log.info("Completed calculating TotalPrice: {}", orderTotal);
        return orderTotal;
    }

    /**
     * Method to build an OrderResponseDTO from Order object
     *
     * @param order Order order
     * @return OrderResponseDTO object which has minimal data from Order
     */
    private OrderResponseDTO buildOrderResponseDTOFromOrder(Order order) {
        log.info("Building OrderResponseDTO from order: {}", order);
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setCustomerId(order.getCustomer().getId());
        orderResponseDTO.setOrderId(order.getId());
        orderResponseDTO.setOrderTotal(order.getOrderTotal());
        orderResponseDTO.setOrderDate(order.getCreateDate());
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
        existingOrder.setLastUpdatedDate(LocalDate.now());
        existingOrder.setOrderTotal(calculateTotalPrice(tacos));

        Order updatedOrder = orderRepository.save(existingOrder);
        return buildOrderResponseDTOFromOrder(updatedOrder);
    }
}
