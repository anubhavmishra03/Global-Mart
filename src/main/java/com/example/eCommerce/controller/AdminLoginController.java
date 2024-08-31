package com.example.eCommerce.controller;

import com.example.eCommerce.DTO.AdminDTO;
import com.example.eCommerce.DTO.LoginDTO;
import com.example.eCommerce.Response.LoginResponse;
import com.example.eCommerce.Response.RegResponse;
import com.example.eCommerce.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("loginResponse")
public class AdminLoginController {
    @Autowired
    private AdminService adminService;

    @GetMapping(path = "/register")
    public String showRegisterPage(RegResponse regResponse, Model model) {
        AdminDTO adminDTO = adminService.loadRegister();
        model.addAttribute("adminDTO", adminDTO);
        model.addAttribute("regResponse", regResponse);
        return "pages/register";
    }

    @PostMapping(path = "/register")
    public String saveAdmin(AdminDTO adminDTO, RedirectAttributes redirectAttributes) {
        RegResponse regResponse = adminService.verifyAdmin(adminDTO);
        if (regResponse.getError().equals(false)) {
            redirectAttributes.addFlashAttribute("adminDTO", adminDTO);
            redirectAttributes.addFlashAttribute("regResponse", regResponse);
            return "redirect:/verify";
        } else {
            redirectAttributes.addFlashAttribute("regResponse", regResponse);
            return "redirect:/register";
        }
    }

    @GetMapping(path = "/verify")
    public String verifyAdmin(AdminDTO adminDTO, RegResponse regResponse,Model model) {
        model.addAttribute("adminDTO", adminDTO);
        model.addAttribute("regResponse", regResponse);
        return "pages/verify";
    }

    @PostMapping(path = "/verify")
    public String processVerify(AdminDTO adminDTO, RedirectAttributes redirectAttributes) {
        if (adminDTO.getOtp().equals(adminDTO.getHiddenOtp())) {
            adminService.addAdmin(adminDTO);
            redirectAttributes.addFlashAttribute("loginResponse", new LoginResponse(true, "Successfully Registered"));
            return "redirect:login";
        }
        redirectAttributes.addFlashAttribute("adminDTO", adminDTO);
        redirectAttributes.addFlashAttribute("regResponse", new RegResponse("Invalid OTP", true));
        return "redirect:/verify";
    }

    @GetMapping(path = "/login")
    public String showLoginPage(LoginResponse loginResponse, Model model) {
        LoginDTO loginDTO = adminService.loadLogin();
        model.addAttribute("loginDTO", loginDTO);
        model.addAttribute("loginResponse", loginResponse);
        return "pages/login";
    }

    @PostMapping(path = "/login")
    public String processLogin(LoginDTO loginDTO, RedirectAttributes redirectAttributes){
        LoginResponse loginResponse = adminService.loginAdmin(loginDTO);
        if (loginResponse.getflag().equals(false)) {
            redirectAttributes.addFlashAttribute("loginResponse", loginResponse);
            return "redirect:products";
        } else {
            redirectAttributes.addFlashAttribute("loginResponse", loginResponse);
            return "redirect:login";
        }
    }
}
