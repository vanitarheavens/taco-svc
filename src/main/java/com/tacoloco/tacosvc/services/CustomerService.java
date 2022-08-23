package com.tacoloco.tacosvc.services;

import com.tacoloco.tacosvc.entities.Customer;
import com.tacoloco.tacosvc.exception.CustomerNotFoundException;
import com.tacoloco.tacosvc.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    /**
     * Method to persist a Customer in the database
     * @param customer Customer the customer details
     * @return Customer updated object
     */
    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Method to update the customer info
     *
     * @param id Long Id of the custmer to update
     * @param customer Customer the new customer details
     * @return Customer updated object
     */
    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isEmpty()) {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }
        Customer existingCustomer = customerOptional.get();
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        return customerRepository.save(existingCustomer);
    }
}
