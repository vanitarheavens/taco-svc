package com.tacoloco.tacosvc.controller;

import com.google.gson.Gson;
import com.tacoloco.tacosvc.dtos.request.OrderRequestDTO;
import com.tacoloco.tacosvc.dtos.request.TacoAndQtyRequestDTO;
import com.tacoloco.tacosvc.dtos.response.OrderResponseDTO;
import com.tacoloco.tacosvc.services.ICustomerService;
import com.tacoloco.tacosvc.services.IOrderService;
import com.tacoloco.tacosvc.services.ITacoService;
import com.tacoloco.tacosvc.util.ObjectToJsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IOrderService orderService;

    @MockBean
    private ICustomerService customerService;

    @MockBean
    private ITacoService tacoService;

    MockHttpServletResponse mockResponse;
    Gson gson;

    @Test
    public void testAddNewMember() throws Exception {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        TacoAndQtyRequestDTO menu1 = new TacoAndQtyRequestDTO();
        menu1.setTacoId(1L);
        menu1.setQuantity(1);
        List<TacoAndQtyRequestDTO> orderItems = List.of(menu1);
        orderRequestDTO.setOrderItems(orderItems);

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setCustomerId(1L);
        orderResponseDTO.setOrderId(4L);
        orderResponseDTO.setOrderTotal(3.0);
        orderResponseDTO.setOrderDate(LocalDate.now());


        when(orderService.createCustomerOrder(1L, orderRequestDTO)).thenReturn(orderResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/{customerId}", 1L)
                        .content(ObjectToJsonUtil.asJsonString(orderRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.orderId").value(4L))
                .andExpect(jsonPath("$.orderTotal").value(3.0));
    }

}
