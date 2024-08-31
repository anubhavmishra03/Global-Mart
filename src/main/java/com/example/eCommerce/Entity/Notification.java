package com.example.eCommerce.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @Column(name = "notificationID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long notificationID;
    private String heading;
    private int adminID;

    private String productName;
    private int quantity;
    private Date date;
    private String customerName;
    private String address;
    @Column(name="city", length=255)
    private String city;

    @Column(name="state", length=255)
    private String state;

    @Column(name="pincode", length=45)
    private String pincode;

    @Column(name = "phone", length=45)
    private String phone;
    private boolean isRead;
    private int price;

    public Notification(String productName, Date date, String heading, int quantity, int adminID, String customerName, String address,  String city, String state, String phone, String pincode, Boolean isRead, int price) {
        this.productName = productName;
        this.date = date;
        this.heading = heading;
        this.quantity = quantity;
        this.adminID = adminID;
        this.customerName = customerName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.phone = phone;
        this.pincode = pincode;
        this.isRead = isRead;
        this.price=price;
    }

    public Notification() {
    }

    public long getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(long notificationID) {
        this.notificationID = notificationID;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
