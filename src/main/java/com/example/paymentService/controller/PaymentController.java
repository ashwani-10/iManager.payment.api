package com.example.paymentService.controller;

import com.example.paymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/payments")
@CrossOrigin(origins = {"http://localhost:63342","http://127.0.0.1:5500"})
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping("/create-order")
    public Map<String, Object> createOrder(@RequestParam String amount,
                                           @RequestParam String currency){
        try {
            System.out.println("inside payment Controller");
            return paymentService.createOrder(Integer.parseInt(amount), currency);
        }catch (Exception e){
            throw new RuntimeException("failed transaction");
        }
    }
}
