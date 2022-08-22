package com.tacoloco.tacosvc.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @NotEmpty(message = "Customer's first name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Customer's last name is required")
    @Column(name = "last_name")
    private String lastName;

}

