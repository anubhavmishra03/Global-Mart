package com.example.eCommerce.Service;

import com.example.eCommerce.DTO.CustomerDTO;
import com.example.eCommerce.DTO.LoginDTO;
import com.example.eCommerce.Response.LoginResponse;
import com.example.eCommerce.Response.RegResponse;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    CustomerDTO loadRegister();
    RegResponse verifyCustomer(CustomerDTO customerDTO);
    void addCustomer(CustomerDTO customerDTO);
    LoginDTO loadSignIn();
    LoginResponse signInCustomer(LoginDTO loginDTO);
}