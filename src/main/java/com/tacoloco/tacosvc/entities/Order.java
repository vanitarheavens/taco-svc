package com.tacoloco.tacosvc.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="customer_order")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "customer_order_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Customer customer;

    @ElementCollection
    private List<Long> tacoList = new ArrayList<>();

    @Column(name = "create_ts")
    private LocalDate createTs;

    @Column(name = "last_updated_ts")
    private LocalDate lastUpdatedTs;

    private double orderTotal;

    public void addTacosToOrder(List<Long> tacos) {
        tacoList.addAll(tacos);
    }

}

