package com.example.eCommerce.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.eCommerce.Entity.WishList;

@EnableJpaRepositories
@Repository
public interface WishListRepo extends JpaRepository<WishList, Integer> {
    List<WishList> findByCustomerID(int customerID);
    void deleteByProductIDAndCustomerID(int productID, int customerID);
}
