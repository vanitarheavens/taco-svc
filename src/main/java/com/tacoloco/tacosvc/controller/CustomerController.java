package com.tacoloco.tacosvc.controller;

import com.tacoloco.tacosvc.entities.Customer;
import com.tacoloco.tacosvc.entities.Order;
import com.tacoloco.tacosvc.services.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@Slf4j(topic ="CustomerController")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    /**
     * GET all customers
     *
     * @return List<Customer>
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        log.info("calling customerService.getAllCustomers");
        List<Customer> customers =  customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    /**
     * GET customer by Id
     *
     * @param customerId Long Id of the customer whose details we want to get
     * @return Customer details
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable Long customerId
    ) {
        Optional<Customer> optionalCustomer = customerService.getCustomerById(customerId);
        return optionalCustomer.map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST or create a new customer
     *
     * @param customerDetails Customer
     * @return Customer with saved details
     */
    @PostMapping
    public ResponseEntity<Customer> createCustomer(
            @Valid @RequestBody Customer customerDetails) {
        Customer customer = customerService.createCustomer(customerDetails);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
}

