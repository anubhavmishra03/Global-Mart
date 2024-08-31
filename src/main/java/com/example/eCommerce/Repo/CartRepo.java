package com.example.eCommerce.Repo;

import com.example.eCommerce.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
    List<Cart> findByCustomerID(int customerID);
    void deleteByProductIDAndCustomerID(int productID, int customerID);
    void deleteByCustomerID(int customerID);
}