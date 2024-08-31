package com.example.eCommerce.DTO;

import org.springframework.stereotype.Component;

@Component
public class AdminDTO {
    private int adminID;
    private String adminName;
    private String email;
    private String password;
    private String captcha;
    private String HiddenCaptcha;
    private String RealCaptcha;
    private String Otp;
    private String HiddenOtp;

    public AdminDTO(int adminID, String adminName, String email, String password, String captcha, String hiddenCaptcha, String realCaptcha, String otp, String hiddenOtp) {
        this.adminID = adminID;
        this.adminName = adminName;
        this.email = email;
        this.password = password;
        this.captcha = captcha;
        HiddenCaptcha = hiddenCaptcha;
        RealCaptcha = realCaptcha;
        Otp = otp;
        HiddenOtp = hiddenOtp;
    }

    public AdminDTO() {
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

    @Override
    public String toString() {
        return "AdminDTO{" +
                "adminID=" + adminID +
                ", adminName='" + adminName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", captcha='" + captcha + '\'' +
                ", HiddenCaptcha='" + HiddenCaptcha + '\'' +
                ", RealCaptcha='" + RealCaptcha + '\'' +
                ", Otp='" + Otp + '\'' +
                ", HiddenOtp='" + HiddenOtp + '\'' +
                '}';
    }
}
