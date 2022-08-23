package com.tacoloco.tacosvc.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TacoAndQtyRequestDTO {

    @NotEmpty(message = "The Taco id should be specified on an order")
    private Long tacoId;

    @NotNull(message = "The quantity of a particular type Tacos should be specified on an order")
    @Positive(message = "The quantity should be greater than zero")
    private int quantity;
}
