package com.tacoloco.tacosvc.services;

import com.tacoloco.tacosvc.dtos.request.OrderRequestDTO;
import com.tacoloco.tacosvc.dtos.request.TacoAndQtyRequestDTO;
import com.tacoloco.tacosvc.dtos.response.OrderResponseDTO;
import com.tacoloco.tacosvc.entities.Customer;
import com.tacoloco.tacosvc.entities.Order;
import com.tacoloco.tacosvc.entities.Taco;
import com.tacoloco.tacosvc.repositories.CustomerRepository;
import com.tacoloco.tacosvc.repositories.OrderRepository;
import com.tacoloco.tacosvc.repositories.TacoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TacoRepository tacoRepository;

    @InjectMocks
    private OrderService orderService;

    private Taco taco1;
    private Taco taco2;
    private List<Taco> tacos;
    private Customer customer;

    @BeforeAll
    void setUp() {
        taco1 = new Taco(1L, "Veggie Taco",  2.5);
        taco2 = new Taco(2L, "Beef Taco",  3.0);
        tacos = List.of(taco1, taco2);

        customer = new Customer(1L, "John", "Doe");
    }

    @Test
    void getAllOrders() {
    }

    @Test
    void getOrderById() {
    }

    @Test
    void createOrder() {

        when(tacoRepository.findById(1L))
                .thenReturn(Optional.of(tacos.get(0)));
        when(tacoRepository.findById(2L))
                .thenReturn(Optional.of(tacos.get(1)));

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        Order order = new Order();
        order.setCustomer(customer);
        order.setCreateDate(LocalDate.now());
        order.setLastUpdatedDate(LocalDate.now());
        order.setOrderTotal(8.8);
        order.setTacoList(List.of(1L, 1L, 2L, 2L));

        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        TacoAndQtyRequestDTO menu1 = new TacoAndQtyRequestDTO();
        menu1.setTacoId(1L);
        menu1.setQuantity(2);
        TacoAndQtyRequestDTO menu2 = new TacoAndQtyRequestDTO();
        menu2.setTacoId(2L);
        menu2.setQuantity(2);
        List<TacoAndQtyRequestDTO> orderItems = List.of(menu1, menu2);
        orderRequestDTO.setOrderItems(orderItems);

        when(orderRepository.save(order)).thenReturn(order);
        OrderResponseDTO response = orderService.createCustomerOrder(1L, orderRequestDTO);
        assertNotNull(response);
        double calculatedOrderTotal = ((2 * 2.5) + (2 * 3.0)) - (((2 * 2.5) + (2 * 3.0)) * 0.2);
        assertEquals(response.getOrderTotal(), calculatedOrderTotal);
        assertEquals(response.getCustomerId(), customer.getId());
    }
}
