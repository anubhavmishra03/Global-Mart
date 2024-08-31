package com.example.eCommerce.controller;

import com.example.eCommerce.DTO.CustomerDTO;
import com.example.eCommerce.DTO.LoginDTO;
import com.example.eCommerce.Response.LoginResponse;
import com.example.eCommerce.Response.RegResponse;
import com.example.eCommerce.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerLoginController {
    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "signup")
    public String showRegisterPage(RegResponse regResponse, Model model) {
        CustomerDTO customerDTO = customerService.loadRegister();
        model.addAttribute("customerDTO", customerDTO);
        model.addAttribute("regResponse", regResponse);
        return "pages/reg";
    }

    @PostMapping(path = "signup")
    public String processRegister(CustomerDTO customerDTO, RedirectAttributes redirectAttributes) {
        RegResponse regResponse = customerService.verifyCustomer(customerDTO);
        if (regResponse.getError().equals(false)) {
            redirectAttributes.addFlashAttribute("customerDTO", customerDTO);
            redirectAttributes.addFlashAttribute("regResponse", regResponse);
            return "redirect:/verification";
        } else {
            redirectAttributes.addFlashAttribute("regResponse", regResponse);
            return "redirect:/signup";
        }
    }

    @GetMapping(path = "verification")
    public String showVerificationPage(CustomerDTO customerDTO, RegResponse regResponse, Model model) {
        model.addAttribute("customerDTO", customerDTO);
        model.addAttribute("regResponse", regResponse);
        return "pages/verification";
    }

    @PostMapping(path = "verification")
    public String processVerification(CustomerDTO customerDTO, RedirectAttributes redirectAttributes) {
        if (customerDTO.getOtp().equals(customerDTO.getHiddenOtp())) {
            customerService.addCustomer(customerDTO);
            LoginResponse loginResponse = new LoginResponse(true, "Successfully Registered");
            redirectAttributes.addFlashAttribute("loginResponse", loginResponse);
            return "redirect:signin";
        }
        redirectAttributes.addFlashAttribute("customerDTO", customerDTO);
        redirectAttributes.addFlashAttribute("regResponse", new RegResponse("Invalid OTP", true));
        return "redirect:/verification";
    }

    @GetMapping(path = "signin")
    public String showSignInPage(LoginResponse loginResponse, Model model) {
        LoginDTO loginDTO = customerService.loadSignIn();
        model.addAttribute("loginDTO", loginDTO);
        model.addAttribute("loginResponse", loginResponse);
        return "pages/signin";
    }

    @PostMapping(path = "signin")
    public String processSignIn(LoginDTO loginDTO, RedirectAttributes redirectAttributes) {
        LoginResponse loginResponse = customerService.signInCustomer(loginDTO);
        if (loginResponse.getflag().equals(false)) {
            redirectAttributes.addFlashAttribute("loginResponse", loginResponse);
            return "redirect:";
        } else {
            redirectAttributes.addFlashAttribute("loginResponse", loginResponse);
            return "redirect:signin";
        }
    }
}
