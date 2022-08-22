package com.tacoloco.tacosvc.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderResponseDTO {

    private Long customerId;

    private Long orderId;

    private double orderTotal;

    private LocalDate orderDate;

}
