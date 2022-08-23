package com.tacoloco.tacosvc.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    private double orderTotal;

    public void addTacosToOrder(List<Long> tacos) {
        tacoList.addAll(tacos);
    }

}

