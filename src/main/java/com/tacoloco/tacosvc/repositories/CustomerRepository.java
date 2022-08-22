package com.tacoloco.tacosvc.repositories;

import com.tacoloco.tacosvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
