package com.example.eCommerce.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="admin")
public class Admin {
    @Id
    @Column(name = "admin_ID", length = 45)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int adminID;

    @Column(name = "admin_Name", length = 255)
    private String adminName;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "password", length = 255)
    private String password;

    public Admin(String password, String email, String adminName, int adminID) {
        this.adminID = adminID;
        this.adminName = adminName;
        this.email = email;
        this.password = password;
    }

    public Admin() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminID=" + adminID +
                ", adminName='" + adminName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
