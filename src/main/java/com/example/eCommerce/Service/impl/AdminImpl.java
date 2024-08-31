package com.example.eCommerce.Service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.eCommerce.Captcha.CaptchaUtil;
import com.example.eCommerce.DTO.AdminDTO;
import com.example.eCommerce.DTO.LoginDTO;
import com.example.eCommerce.Entity.Admin;
import com.example.eCommerce.Repo.AdminRepo;
import com.example.eCommerce.Response.LoginResponse;
import com.example.eCommerce.Response.RegResponse;
import com.example.eCommerce.Service.AdminService;

import cn.apiclub.captcha.Captcha;

@Service
public class AdminImpl implements AdminService {
    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public AdminDTO loadRegister() {
        AdminDTO adminDTO = new AdminDTO();
        getCaptcha(adminDTO);
        String Otp = generateOTP();
        adminDTO.setHiddenOtp(Otp);
        return adminDTO;
    }

    @Override
    public RegResponse verifyAdmin(AdminDTO adminDTO) {
        if (adminDTO.getCaptcha().equals(adminDTO.getHiddenCaptcha())) {
            Admin admin = adminRepo.findByEmail(adminDTO.getEmail());
            if (admin != null) {
                return new RegResponse("Email Already Exists", true);
            } else {
                sendVerificationEmail(adminDTO.getEmail(), adminDTO.getHiddenOtp());
                return new RegResponse("Success", false);
            }
        } else {
            return new RegResponse("Incorrect Captcha", true);
        }
    }

    @Override
    public void addAdmin(AdminDTO adminDTO) {
        Admin admin = new Admin(
                this.passwordEncoder.encode(adminDTO.getPassword()),
                adminDTO.getEmail(),
                adminDTO.getAdminName(),
                adminDTO.getAdminID()
        );
        adminRepo.save(admin);
    }

    @Override
    public LoginDTO loadLogin() {
        LoginDTO loginDTO = new LoginDTO();
        getLoginCaptcha(loginDTO);
        return loginDTO;
    }

    @Override
    public LoginResponse loginAdmin(LoginDTO loginDTO) {
        if (loginDTO.getCaptcha().equals(loginDTO.getHiddenCaptcha())) {
            Admin admin1 = adminRepo.findByEmail(loginDTO.getEmail());
            if(admin1 != null) {
                if (passwordEncoder.matches(loginDTO.getPassword(), admin1.getPassword())) {
                    LoginResponse loginResponse = new LoginResponse();
                    loginResponse.setAdminID(admin1.getAdminID());
                    loginResponse.setMessage("Login successful");
                    loginResponse.setflag(false);
                    return loginResponse;
                } else {
                    return new LoginResponse(true, "Username or Password Incorrect");
                }
            }else {
                return new LoginResponse(true, "Email does not exist");
            }
        } else {
            return new LoginResponse(true, "Incorrect Captcha");
        }
    }

    private void getCaptcha(AdminDTO adminDTO) {
        Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
        adminDTO.setHiddenCaptcha(captcha.getAnswer());
        adminDTO.setCaptcha(""); // value entered by the User
        adminDTO.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
    }

    private String generateOTP(){
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }

    private void sendVerificationEmail(String email,String otp){
        String subject = "Email verification";
        String body ="your verification otp is: "+otp;
        System.out.println(otp);
    //    emailService.sendEmail(email,subject,body);
    }

    private void getLoginCaptcha(LoginDTO loginDTO) {
        Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
        loginDTO.setHiddenCaptcha(captcha.getAnswer());
        loginDTO.setCaptcha(""); // value entered by the User
        loginDTO.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
    }
}
