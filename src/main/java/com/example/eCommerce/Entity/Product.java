package com.example.eCommerce.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "productID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productID;

    private int adminID;

    private String adminName;

    @Lob
    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "productName", length = 255)
    private String productName;

    @Column(name = "category", length = 255)
    private String category;

    @Column(name = "status", length = 255)
    private String status;

    @Column(name = "sales", length = 45)
    private int sales;

    @Column(name = "stock", length = 45)
    private int stock;

    @Column(name = "price", length = 45)
    private int price;

    @Column(name = "date")
    private Date date;

    public Product(String image, String description, String productName, String category, String status, int sales, int stock, int price, Date date) {
        this.image = image;
        this.description = description;
        this.productName = productName;
        this.category = category;
        this.status = status;
        this.sales = sales;
        this.stock = stock;
        this.price = price;
        this.date = date;
    }

    public Product() {
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
