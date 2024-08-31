package com.example.eCommerce.Repo;

import com.example.eCommerce.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {
    Admin findByEmail(String email);
    Admin findByAdminID(int adminId);
}
