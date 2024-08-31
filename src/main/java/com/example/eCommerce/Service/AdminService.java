package com.example.eCommerce.Service;

import org.springframework.stereotype.Service;

import com.example.eCommerce.DTO.AdminDTO;
import com.example.eCommerce.DTO.LoginDTO;
import com.example.eCommerce.Response.LoginResponse;
import com.example.eCommerce.Response.RegResponse;

@Service
public interface AdminService {
    void addAdmin(AdminDTO adminDTO);
    LoginResponse loginAdmin(LoginDTO loginDTO);
    RegResponse verifyAdmin(AdminDTO adminDTO);
    AdminDTO loadRegister();
    LoginDTO loadLogin();
}
