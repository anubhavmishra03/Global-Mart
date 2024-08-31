package com.example.eCommerce.Repo;

import com.example.eCommerce.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Customer findByEmail(String email);
    Customer findByCustomerID(int customerID);
}
