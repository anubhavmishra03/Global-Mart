package com.example.eCommerce.Repo;

import com.example.eCommerce.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findByAdminID(int id);
    List<Product> findByProductName(String productName);
    List<Product> findByAdminIDAndCategory(int adminID, String category);
    List<Product> findByAdminIDAndStatus(int adminID, String status);
    List<Product> findByAdminIDAndCategoryAndStatus(int adminID, String category, String status);
    Product findByProductID(int id);
    List<Product> findByProductNameContainingOrDescriptionContaining(String productName, String description);
    List<Product> findByCategory(String category);
    List<Product> findByProductIDIn(List<Integer> productID);
}