package com.example.eCommerce.DTO;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.*;

public class ProductDTO {
    private MultipartFile image;

    @Size(min = 10,message = "The description should be min of 10")
    @Size(max = 1000,message = "The description should be not more than 100 characters")
    private String description;

    private  String productName;
    private  String category;
    private int stock;
    private  int price;

    public ProductDTO(MultipartFile image, int price, int stock, String category, String productName, String description) {
        this.image = image;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.productName = productName;
        this.description = description;
    }

    public ProductDTO() {
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public @Size(min = 10, message = "The description should be min of 10") @Size(max = 1000, message = "The description should be not more than 100 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 10, message = "The description should be min of 10") @Size(max = 1000, message = "The description should be not more than 100 characters") String description) {
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
}
