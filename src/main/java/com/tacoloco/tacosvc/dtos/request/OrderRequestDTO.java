package com.tacoloco.tacosvc.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestDTO {

    @NotEmpty(message = "An order should have atleast one Taco")
    private List<TacoAndQtyRequestDTO> orderItems;
}
