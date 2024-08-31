package com.example.eCommerce.Response;

public class LoginResponse {
    String message;
    Boolean flag;
    int adminID;
    int customerIdentification;

    public LoginResponse(Boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public LoginResponse() {
    }

    public String getMessage() {
        return message;
    }

    public Boolean getflag() {
        return flag;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setflag(Boolean flag) {
        this.flag = flag;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public int getCustomerIdentification() {
        return customerIdentification;
    }

    public void setCustomerIdentification(int customerIdentification) {
        this.customerIdentification = customerIdentification;
    }
}
