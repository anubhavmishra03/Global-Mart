package com.example.eCommerce.controller;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.eCommerce.Entity.Admin;
import com.example.eCommerce.Entity.Cart;
import com.example.eCommerce.Entity.Customer;
import com.example.eCommerce.Entity.Notification;
import com.example.eCommerce.Entity.Product;
import com.example.eCommerce.Entity.WishList;
import com.example.eCommerce.Repo.AdminRepo;
import com.example.eCommerce.Repo.CartRepo;
import com.example.eCommerce.Repo.CustomerRepo;
import com.example.eCommerce.Repo.NotificationRepo;
import com.example.eCommerce.Repo.ProductRepo;
import com.example.eCommerce.Repo.WishListRepo;
import com.example.eCommerce.Response.LoginResponse;

import jakarta.transaction.Transactional;


@Controller
@SessionAttributes("loginResponse")
public class ProductController {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private WishListRepo wishListRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private AdminRepo adminRepo;

    @GetMapping(path = "/")
    public String index(LoginResponse loginResponse, Model model, @RequestParam(value = "category", required = false) String category) {
        Customer customer = customerRepo.findByCustomerID(loginResponse.getCustomerIdentification());
        if (customer != null) {
            model.addAttribute("isLogin", true);
            var username = customer.getCustomerName();
            var firstLetter = username.substring(0, 1).toUpperCase();
            model.addAttribute("username", username);
            model.addAttribute("firstLetter", firstLetter);
        }
        if (category != null) {
            model.addAttribute("products", productRepo.findByCategory(category));
        } else {
            model.addAttribute("products", productRepo.findAll());
        }
        return "pages/home";
    }

    @GetMapping(path = "/product")
    public String showProduct(Model model, @RequestParam int id, LoginResponse loginResponse) {
        Customer customer = customerRepo.findByCustomerID(loginResponse.getCustomerIdentification());
        if (customer != null) {
            model.addAttribute("isLogin", true);
            var username = customer.getCustomerName();
            var firstLetter = username.substring(0, 1).toUpperCase();
            model.addAttribute("username", username);
            model.addAttribute("firstLetter", firstLetter);
        }
        model.addAttribute("product", productRepo.findByProductID(id));
        return "pages/product";
    }

    @PostMapping(path = "/addToCart")
    ResponseEntity<String> addToCart(@RequestParam int id, LoginResponse loginResponse){
        Customer customer = customerRepo.findByCustomerID(loginResponse.getCustomerIdentification());
        if (customer != null) {
            Product product = productRepo.findByProductID(id);
            Cart cart = new Cart(id, loginResponse.getCustomerIdentification(), product.getProductName(), product.getDescription(), product.getPrice(), 1);
            cartRepo.save(cart);
            return ResponseEntity.ok("Product Added to Cart Successfully");
        } else {
            return ResponseEntity.ok("Please Signin to use cart");
        }
    }

    @PostMapping(path = "/addToWishList")
    ResponseEntity<String> addToWishList(@RequestParam int id, LoginResponse loginResponse){
        Customer customer = customerRepo.findByCustomerID(loginResponse.getCustomerIdentification());
        if (customer != null) {
            WishList wishList = new WishList(loginResponse.getCustomerIdentification(), id);
            wishListRepo.save(wishList);
            return ResponseEntity.ok("Product Added to Wishlist Successfully");
        } else {
            return ResponseEntity.ok("Please Signin to use wishlist");
        }
    }

    @PostMapping(path = "/logout")
    public String handleLogout(RedirectAttributes redirectAttributes) {
        LoginResponse loginResponse = new LoginResponse();
        redirectAttributes.addFlashAttribute("loginResponse", loginResponse);
        return "redirect:/";
    }

    @GetMapping(path = "/search")
    public String searchItems(LoginResponse loginResponse, @RequestParam(value = "keyword") String keyword, @RequestParam(value = "price", required = false) String price, Model model) {
        Customer customer = customerRepo.findByCustomerID(loginResponse.getCustomerIdentification());
        if (customer != null) {
            model.addAttribute("isLogin", true);
            var username = customer.getCustomerName();
            var firstLetter = username.substring(0, 1).toUpperCase();
            model.addAttribute("username", username);
            model.addAttribute("firstLetter", firstLetter);
        }
        List<Product> searchResults = productRepo.findByProductNameContainingOrDescriptionContaining(keyword, keyword);
        if (price != null) {
            if (price.equals("asc")) {
                searchResults.sort(Comparator.comparing(Product::getPrice));
            } else {
                searchResults.sort(Comparator.comparing(Product::getPrice).reversed());
            }
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchResults", searchResults);
        return "pages/search";
    }

    @GetMapping(path = "/cart")
    public String showCart(LoginResponse loginResponse, Model model) {
        Customer customer = customerRepo.findByCustomerID(loginResponse.getCustomerIdentification());
        if (customer != null) {
            List<Cart> cart = cartRepo.findByCustomerID(loginResponse.getCustomerIdentification());
            model.addAttribute("cart", cart);
            return "pages/cart";
        }
        return "redirect:/signin";
    }

    @GetMapping(path = "/deleteFromCart")
    @Transactional
    public String handleCart(LoginResponse loginResponse, @RequestParam int id) {
        cartRepo.deleteByProductIDAndCustomerID(id, loginResponse.getCustomerIdentification());
        return "redirect:/cart";
    }

    @GetMapping(path = "/wishlist")
    public String showWishlist(LoginResponse loginResponse, Model model) {
        Customer customer = customerRepo.findByCustomerID(loginResponse.getCustomerIdentification());
        if (customer != null) {
            List<Integer> wishList = wishListRepo.findByCustomerID(loginResponse.getCustomerIdentification()).stream().map(WishList::getProductID).toList();
            List<Product> products = productRepo.findByProductIDIn(wishList);
            model.addAttribute("products", products);
            return "pages/wishList";
        }
        return "redirect:/signin";
    }

    @GetMapping(path = "/deleteFromWishlist")
    @Transactional
    public String handleWishlist(LoginResponse loginResponse, @RequestParam int id) {
        wishListRepo.deleteByProductIDAndCustomerID(id, loginResponse.getCustomerIdentification());
        return "redirect:/wishlist";
    }

    @GetMapping(path = "/orderSummary")
    public String showOrderSummaryPage(LoginResponse loginResponse, Model model, @RequestParam("quantity") List<Integer> quantity) {
        Customer customer = customerRepo.findByCustomerID(loginResponse.getCustomerIdentification());
        if (customer != null) {
            List<Cart> cart = cartRepo.findByCustomerID(loginResponse.getCustomerIdentification());
            var total = 0;
            for (int i=0; i < cart.size(); i++) {
                Cart cartItem = cart.get(i);
                Integer q = quantity.get(i);
                cartItem.setQuantity(q);
                cartRepo.save(cartItem);
                total += (cartItem.getPrice() * q);
            }
            model.addAttribute("total", total);
            model.addAttribute("cart", cart);
            return "pages/orderSummary";
        }
        return "redirect:/signin";
    }

    @GetMapping("/userDetails")
    public String showUserDetails(LoginResponse loginResponse, Model model) {
        Customer customer = customerRepo.findByCustomerID(loginResponse.getCustomerIdentification());
        if (customer != null) {
            model.addAttribute("customer", customer);
            return "pages/userDetails";
        }
        return "redirect:/signin";
    }

    @GetMapping("/newOrder")
    @Transactional
    public String handleOrder(LoginResponse loginResponse, @RequestParam String address, @RequestParam String city, @RequestParam String state, @RequestParam String pincode) {
        Customer customer = customerRepo.findByCustomerID(loginResponse.getCustomerIdentification());
        if (customer == null) {
            return "redirect:/signin";
        }
        String customerEmailBody = "Dear " + customer.getCustomerName() + ",\n\n" +
        "Thank you for your order! Here are the details of your purchase:\n\n" +
        "Order Summary:\n";

        List<Cart> cart = cartRepo.findByCustomerID(loginResponse.getCustomerIdentification());
        for (Cart cartItem: cart) {
            Product product = productRepo.findByProductID(cartItem.getProductID());
            Admin admin = adminRepo.findByAdminID(product.getAdminID());
            Notification notification = new Notification(
                cartItem.getProductName(),
                new Date(),
                "New Order",
                cartItem.getQuantity(),
                product.getAdminID(),
                customer.getCustomerName(),
                address,
                city,
                state,
                customer.getPhone(),
                pincode,
                false,
                product.getPrice()
            );
            notificationRepo.save(notification);
            product.setSales(product.getSales() + cartItem.getQuantity());
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepo.save(product);
            
            customerEmailBody += "- Product Name: " + cartItem.getProductName() + "\n" +
            "  Quantity: " + cartItem.getQuantity() + "\n" +
            "  Price: $" + product.getPrice() + "\n\n";

            String adminEmailBody = "Dear " + admin.getAdminName() + ",\n\n" +
            "A new order has been placed by " + customer.getCustomerName() + ". Here are the details:\n\n" +
            "Order Summary:\n";

            adminEmailBody += """
                              Please prepare the order for shipment.
                              
                              Best regards,
                              Global Mart""";

            adminEmailBody += "- Product Name: " + cartItem.getProductName() + "\n" +
            "  Quantity: " + cartItem.getQuantity() + "\n" +
            "  Customer Name: " + customer.getCustomerName() + "\n" +
            "  Shipping Address: " + address + ", " + city + ", " + state + "\n\n";
            sendVerificationEmail(admin.getEmail(), adminEmailBody);
        }
        customerEmailBody += "Shipping Address:\n" +
        address + ", " + city + ", " + state + "\n\n" +
        "We will notify you once your order has been shipped.\n\n" +
        "Thank you for shopping with us!\n\n" +
        "Best regards,\n" +
        "Global Mart";
        sendVerificationEmail(customer.getEmail(), customerEmailBody);
        cartRepo.deleteByCustomerID(loginResponse.getCustomerIdentification());
        return "redirect:/";
    }
    

    private void sendVerificationEmail(String email,String body){
        String subject = "New Order";
        System.out.println(body);
    //    emailService.sendEmail(email,subject,body);
    }
}