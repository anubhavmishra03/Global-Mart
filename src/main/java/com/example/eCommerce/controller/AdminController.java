package com.example.eCommerce.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.eCommerce.DTO.ProductDTO;
import com.example.eCommerce.DTO.SalesDTO;
import com.example.eCommerce.DTO.SalesDataDTO;
import com.example.eCommerce.Entity.Admin;
import com.example.eCommerce.Entity.Notification;
import com.example.eCommerce.Entity.Product;
import com.example.eCommerce.Repo.AdminRepo;
import com.example.eCommerce.Repo.NotificationRepo;
import com.example.eCommerce.Repo.ProductRepo;
import com.example.eCommerce.Response.LoginResponse;

@Controller
@SessionAttributes("loginResponse")
public class AdminController {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    @GetMapping(path = "/products")
    public String showProductsPage(LoginResponse loginResponse, Model model, RedirectAttributes redirectAttributes, @RequestParam(value = "category", required = false) String category, @RequestParam(value = "status", required = false) String status, @RequestParam(value = "item", required = false) String item) {
        Admin admin = adminRepo.findByAdminID(loginResponse.getAdminID());
        if (admin==null) {
            LoginResponse loginResponse1 = new LoginResponse(true, "Session Expired");
            redirectAttributes.addFlashAttribute("loginResponse", loginResponse1);
            return "redirect:/login";
        }
        if (category != null & status==null) {
            model.addAttribute("products", productRepo.findByAdminIDAndCategory(loginResponse.getAdminID(), category));
        } else if (category==null & status!=null) {
            model.addAttribute("products", productRepo.findByAdminIDAndStatus(loginResponse.getAdminID(), status));
        } else if (category!=null & status!=null) {
            model.addAttribute("products", productRepo.findByAdminIDAndCategoryAndStatus(loginResponse.getAdminID(), category, status));
        } else {
            model.addAttribute("products", productRepo.findByAdminID(loginResponse.getAdminID()));
        }
        if (item!=null) {
            model.addAttribute("products", productRepo.findByProductName(item));
        }
        var username = admin.getAdminName();
        var firstLetter = username.substring(0, 1).toUpperCase();
        model.addAttribute("firstLetter", firstLetter);
        model.addAttribute("username", username);
        model.addAttribute("loginResponse", loginResponse);

        boolean hasNewNotification = !notificationRepo.findByAdminIDAndIsReadFalse(loginResponse.getAdminID()).isEmpty();
        model.addAttribute("hasNewNotification", hasNewNotification);
        return "pages/dashboard_product";
    }

    @GetMapping(path = "/addproduct")
    public String showAddProductPage(Model model, LoginResponse loginResponse, RedirectAttributes redirectAttributes) {
        Admin admin = adminRepo.findByAdminID(loginResponse.getAdminID());
        if (admin==null) {
            LoginResponse loginResponse1 = new LoginResponse(true, "Session Expired");
            redirectAttributes.addFlashAttribute("loginResponse", loginResponse1);
            return "redirect:/login";
        }
        var username = admin.getAdminName();
        var firstLetter = username.substring(0, 1).toUpperCase();
        model.addAttribute("firstLetter", firstLetter);
        model.addAttribute("username", username);
        ProductDTO productDTO = new ProductDTO();
        model.addAttribute("loginResponse", loginResponse);
        model.addAttribute("productDTO", productDTO);
        return "pages/addProduct";
    }

    @PostMapping(path = "/addproduct")
    public String processAddProduct(ProductDTO productDTO, RedirectAttributes redirectAttributes, LoginResponse loginResponse) {
        MultipartFile image = productDTO.getImage();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime()+"_"+image.getOriginalFilename();
        try {
            String uploadDir="public/images/";
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream=image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir+storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Product product1 = new Product(
                storageFileName,
                productDTO.getDescription(),
                productDTO.getProductName(),
                productDTO.getCategory(),
                "Active",
                0,
                productDTO.getStock(),
                productDTO.getPrice(),
                createdAt
        );
        product1.setAdminID(loginResponse.getAdminID());
        Admin admin = adminRepo.findByAdminID(loginResponse.getAdminID());
        product1.setAdminName(admin.getAdminName());
        productRepo.save(product1);
        loginResponse.setflag(false);
        loginResponse.setMessage("Product added successfully");
        redirectAttributes.addFlashAttribute("loginResponse", loginResponse);
        return "redirect:products";
    }

    @GetMapping(path = "/delete")
    public String deleteProduct(@RequestParam int id, LoginResponse loginResponse, RedirectAttributes redirectAttributes) {
        try {
            Product product = productRepo.findById(id).get();
            if (loginResponse.getAdminID() != product.getAdminID()) {
                LoginResponse loginResponse1 = new LoginResponse(true, "Session Expired");
                redirectAttributes.addFlashAttribute("loginResponse", loginResponse1);
                return "redirect:/login";
            }
            Path imagePath=Paths.get("public/images/"+product.getImage());

            try {
                Files.delete(imagePath);
            }catch (Exception ex){
                System.out.println("Exception: "+ex.getMessage());
            }
            productRepo.delete(product);


        }catch (Exception ex){
            System.out.println("Exception: "+ex.getMessage());
        }

        return "redirect:products";
    }

    @GetMapping(path = "/edit")
    public String showEditProductPage(Model model, @RequestParam int id, LoginResponse loginResponse, RedirectAttributes redirectAttributes) {
        try{
            Product product=productRepo.findById(id).get();
            if (loginResponse.getAdminID() != product.getAdminID()) {
                LoginResponse loginResponse1 = new LoginResponse(true, "Session Expired");
                redirectAttributes.addFlashAttribute("loginResponse", loginResponse1);
                return "redirect:/login";
            }

            model.addAttribute("product",product);

            ProductDTO productDTO=new ProductDTO();
            productDTO.setProductName(product.getProductName());
            productDTO.setStock(product.getStock());
            productDTO.setCategory(product.getCategory());
            productDTO.setPrice(product.getPrice());
            productDTO.setDescription(product.getDescription());

            model.addAttribute("productDTO",productDTO);

        }catch(Exception ex){
            System.out.println("Exception: "+ ex.getMessage());
            return "redirect:/products";
        }

        return "/pages/editProduct";
    }

    @PostMapping("/edit")
    public  String updateProduct(Model model, @RequestParam int id, ProductDTO productDTO){
        try {
            Product product=productRepo.findById(id).get();
            model.addAttribute("product",product);


            //Now its time to update the new Images with old one
            if (!productDTO.getImage().isEmpty()){
                //delete old image
                String uploadDir="public/images/";
                Path oldImagePath=Paths.get(uploadDir+product.getImage());
                try {
                    Files.delete(oldImagePath);
                }catch (Exception ex){
                    System.out.println("Exception: "+ex.getMessage());
                }
                //saving new file image;

                MultipartFile image=productDTO.getImage();
                Date createdAt=new Date();
                String storageFileName=createdAt.getTime() +"_"+image.getOriginalFilename();

                try (InputStream inputStream=image.getInputStream()) {
                    Files.copy(inputStream,Paths.get(uploadDir+storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                product.setImage(storageFileName);
            }
            product.setProductName(productDTO.getProductName());
            product.setStock(productDTO.getStock());
            product.setCategory(productDTO.getCategory());
            product.setPrice(productDTO.getPrice());
            product.setDescription(productDTO.getDescription());
            productRepo.save(product);


        }catch (Exception ex){
            System.out.println("Exception: "+ex.getMessage());

        }
        return "redirect:/products";
    }

    @GetMapping(path = "/notification")
    public String showNotifications(LoginResponse loginResponse, Model model, RedirectAttributes redirectAttributes) {
        Admin admin = adminRepo.findByAdminID(loginResponse.getAdminID());
        if (admin==null) {
            LoginResponse loginResponse1 = new LoginResponse(true, "Session Expired");
            redirectAttributes.addFlashAttribute("loginResponse", loginResponse1);
            return "redirect:/login";
        }
        var username = admin.getAdminName();
        var firstLetter = username.substring(0, 1).toUpperCase();
        model.addAttribute("firstLetter", firstLetter);
        model.addAttribute("username", username);
        List<Notification> notificationList = notificationRepo.findByAdminID(loginResponse.getAdminID());
        notificationList.sort(Comparator.comparing(Notification::getDate).reversed());
        model.addAttribute("notification", notificationList);

        List<Notification> unreadNotifications = notificationRepo.findByAdminIDAndIsReadFalse(loginResponse.getAdminID());
        for (Notification notif: unreadNotifications) {
            notif.setRead(true);
            notificationRepo.save(notif);
        }
        return "/pages/dashboard_notification";
    }

    @GetMapping(path="/stats")
    public String showStats(LoginResponse loginResponse, RedirectAttributes redirectAttributes, Model model){
        Admin admin = adminRepo.findByAdminID(loginResponse.getAdminID());
        if (admin==null) {
            LoginResponse loginResponse1 = new LoginResponse(true, "Session Expired");
            redirectAttributes.addFlashAttribute("loginResponse", loginResponse1);
            return "redirect:/login";
        }
        var username = admin.getAdminName();
        var firstLetter = username.substring(0, 1).toUpperCase();
        model.addAttribute("firstLetter", firstLetter);
        model.addAttribute("username", username);


        //Alltime Product Sales
        var totalSales = 0;
        var totalOrders = 0;
        List<Notification> notifications = notificationRepo.findByAdminID(loginResponse.getAdminID());
        for (Notification notification: notifications) {
            totalSales += (notification.getQuantity() * notification.getPrice());
            totalOrders += notification.getQuantity();
        }
        notifications.sort(Comparator.comparing(Notification::getQuantity).reversed());
        model.addAttribute("notifications", notifications);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalOrders", totalOrders);


        // Last Month Sales
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date lastMonth = calendar.getTime();
        List<Object[]> lastMonthSales = notificationRepo.findProductDetailsByAdminIDWithinLastMonth(loginResponse.getAdminID(), lastMonth);
        List<SalesDTO> sales = lastMonthSales.stream()
            .map(result -> new SalesDTO((String) result[0], (Long) result[1], (Long) result[2]))
            .collect(Collectors.toList());
        var lastMonthTotalPrice = 0;
        for (SalesDTO sale: sales) {
            lastMonthTotalPrice += (sale.getQuantity() * sale.getPrice());
        }
        model.addAttribute("lastMonthTotalPrice", lastMonthTotalPrice);
        model.addAttribute("lastMonthSales", sales);
        calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date lastTwoMonth = calendar.getTime();
        List<Object[]> lastTwoMonthSales = notificationRepo.findProductDetailsByAdminIDWithinLastMonth(loginResponse.getAdminID(), lastTwoMonth);
        List<SalesDTO> salesTwo = lastTwoMonthSales.stream().map(result -> new SalesDTO((String) result[0], (Long) result[1], (Long) result[2])).collect(Collectors.toList());
        var lastTwoMonthTotalPrice = 0;
        for (SalesDTO saleTwo: salesTwo) {
            lastTwoMonthTotalPrice += (saleTwo.getQuantity() * saleTwo.getPrice());
        }
        if (lastTwoMonthTotalPrice != lastMonthTotalPrice) {
            var salesGrowth = (((2*lastMonthTotalPrice) - lastTwoMonthTotalPrice)/(lastTwoMonthTotalPrice - lastMonthTotalPrice)) * 100;
            model.addAttribute("salesGrowth", salesGrowth);
        }
        

        // Last Year Graph
        calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        Date lastYearStart = calendar.getTime();
        List<Object[]> lastYearSales = notificationRepo.findSalesDataBetweenDates(lastYearStart, new Date());
        List<SalesDataDTO> lastYearSalesData = lastYearSales.stream()
        .map(result -> new SalesDataDTO((Integer) result[0], (Long) result[1]))
        .collect(Collectors.toList());
        var prevYearSales = 0;
        for (SalesDataDTO sale: lastYearSalesData) {
            prevYearSales += sale.getTotalSales();
        }
        model.addAttribute("prevYearSales", prevYearSales);
        model.addAttribute("lastYearSalesData", lastYearSalesData);
        return "pages/dashboard_stats";
    }
}
