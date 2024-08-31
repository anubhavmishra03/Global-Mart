package com.example.eCommerce.DTO;

import org.springframework.stereotype.Component;

@Component
public class CustomerDTO {
    private String customerName;
    private String address;
    private String password;
    private String email;
    private String captcha;
    private String HiddenCaptcha;
    private String RealCaptcha;
    private String Otp;
    private String HiddenOtp;
    private String city;
    private String state;
    private String pincode;
    private String phone;

    public CustomerDTO(String customerName, String address, String password, String email, String captcha, String hiddenCaptcha, String realCaptcha, String otp, String hiddenOtp,  String city, String state, String phone, String pincode) {
        this.customerName = customerName;
        this.address = address;
        this.password = password;
        this.email = email;
        this.captcha = captcha;
        HiddenCaptcha = hiddenCaptcha;
        RealCaptcha = realCaptcha;
        Otp = otp;
        HiddenOtp = hiddenOtp;
        this.city = city;
        this.state = state;
        this.phone = phone;
        this.pincode = pincode;
    }

    public CustomerDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getHiddenCaptcha() {
        return HiddenCaptcha;
    }

    public void setHiddenCaptcha(String hiddenCaptcha) {
        HiddenCaptcha = hiddenCaptcha;
    }

    public String getRealCaptcha() {
        return RealCaptcha;
    }

    public void setRealCaptcha(String realCaptcha) {
        RealCaptcha = realCaptcha;
    }

    public String getOtp() {
        return Otp;
    }

    public void setOtp(String otp) {
        Otp = otp;
    }

    public String getHiddenOtp() {
        return HiddenOtp;
    }

    public void setHiddenOtp(String hiddenOtp) {
        HiddenOtp = hiddenOtp;
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
}
