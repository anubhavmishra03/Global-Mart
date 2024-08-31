package com.example.eCommerce.Repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.eCommerce.Entity.Notification;

@EnableJpaRepositories
@Repository
public interface NotificationRepo extends JpaRepository<Notification, Integer> {
    List<Notification> findByAdminID(int id);
    List<Notification> findByAdminIDAndIsReadFalse(int adminID);

    @Query("SELECT p.productName, SUM(p.quantity), SUM(p.price) FROM Notification p WHERE p.adminID = :adminID AND p.date >= :lastMonth GROUP BY p.productName")
    List<Object[]> findProductDetailsByAdminIDWithinLastMonth(@Param("adminID") int adminID, @Param("lastMonth") Date lastMonth);

    @Query("SELECT MONTH(s.date), SUM(s.price * s.quantity) FROM Notification s WHERE s.date BETWEEN :startDate AND :endDate GROUP BY MONTH(s.date)")
    List<Object[]> findSalesDataBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}