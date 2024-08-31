package com.example.eCommerce.Service.impl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.eCommerce.Captcha.CaptchaUtil;
import com.example.eCommerce.DTO.CustomerDTO;
import com.example.eCommerce.DTO.LoginDTO;
import com.example.eCommerce.Entity.Customer;
import com.example.eCommerce.Repo.CustomerRepo;
import com.example.eCommerce.Response.LoginResponse;
import com.example.eCommerce.Response.RegResponse;
import com.example.eCommerce.Service.CustomerService;

import cn.apiclub.captcha.Captcha;

@Service
public class CustomerImpl implements CustomerService {
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CustomerDTO loadRegister() {
        CustomerDTO customerDTO = new CustomerDTO();
        getCaptcha(customerDTO);
        String otp = generateOTP();
        customerDTO.setHiddenOtp(otp);
        return customerDTO;
    }

    @Override
    public RegResponse verifyCustomer(CustomerDTO customerDTO) {
        if (customerDTO.getCaptcha().equals(customerDTO.getHiddenCaptcha())) {
            Customer customer = customerRepo.findByEmail(customerDTO.getEmail());
            if (customer != null) {
                return new RegResponse("Email Already Exists", true);
            } else {
                sendVerificationEmail(customerDTO.getEmail(), customerDTO.getHiddenOtp());
                return new RegResponse("Success", false);
            }
        } else {
            return new RegResponse("Incorrect Captcha", true);
        }
    }

    @Override
    public void addCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO.getCustomerName(),
                customerDTO.getEmail(),
                this.passwordEncoder.encode(customerDTO.getPassword()),
                customerDTO.getAddress(),
                customerDTO.getCity(),
                customerDTO.getState(),
                customerDTO.getPhone(),
                customerDTO.getPincode()
        );
        customerRepo.save(customer);
    }

    @Override
    public LoginDTO loadSignIn() {
        LoginDTO loginDTO = new LoginDTO();
        getLoginCaptcha(loginDTO);
        return loginDTO;
    }

    @Override
    public LoginResponse signInCustomer(LoginDTO loginDTO) {
        if (loginDTO.getCaptcha().equals(loginDTO.getHiddenCaptcha())) {
            Customer customer = customerRepo.findByEmail(loginDTO.getEmail());
            if (customer != null) {
                if (passwordEncoder.matches(loginDTO.getPassword(), customer.getPassword())) {
                    LoginResponse loginResponse = new LoginResponse();
                    loginResponse.setCustomerIdentification(customer.getCustomerID());
                    loginResponse.setMessage("Login successful");
                    loginResponse.setflag(false);
                    return loginResponse;
                } else {
                    return new LoginResponse(true, "Username or Password Incorrect");
                }
            } else {
                return new LoginResponse(true, "Email does not exist");
            }
        } else {
            return new LoginResponse(true, "Incorrect Captcha");
        }
    }

    private void getCaptcha(CustomerDTO customerDTO) {
        Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
        customerDTO.setHiddenCaptcha(captcha.getAnswer());
        customerDTO.setCaptcha(""); // value entered by the User
        customerDTO.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
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
