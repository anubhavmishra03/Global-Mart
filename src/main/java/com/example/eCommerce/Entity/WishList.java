package com.example.eCommerce.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "wishList")
public class WishList {
    @Id
    @Column(name = "wishListID", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int wishListID;

    private int customerID;

    private int productID;

    public WishList(int customerID, int productID) {
        this.customerID = customerID;
        this.productID = productID;
    }

    public WishList() {
    }

    public int getWishListID() {
        return wishListID;
    }

    public void setWishListID(int wishListID) {
        this.wishListID = wishListID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}
