package com.example.eCommerce.DTO;

import org.springframework.stereotype.Component;

@Component
public class SalesDataDTO {
    private int month;
    private long totalSales;

    public SalesDataDTO(int month, long totalSales) {
        this.month = month;
        this.totalSales = totalSales;
    }

    public SalesDataDTO() {
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public long getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(long totalSales) {
        this.totalSales = totalSales;
    }
}
