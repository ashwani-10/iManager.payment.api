package com.example.paymentService.controller;

import com.example.paymentService.service.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("api/webhook")
public class RazorPayWebhookController {

    @Autowired
    MessageProducer messageProducer;

    @PostMapping("/razorpay")
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String,Object> payload){
        try{
            String event = (String) payload.get("event");

            Map<String,Object> payment = (Map<String, Object>) payload.get("payload");
            Map<String,Object> paymentData = (Map<String, Object>) payment.get("payment");
            Map<String,Object> paymentEntity = (Map<String, Object>) paymentData.get("entity");

            String orderId = (String) paymentEntity.get("order_id");
            String status = (String) paymentEntity.get("status");
            String paymentId = (String) paymentEntity.get("id");
            Integer amount = (Integer) paymentEntity.get("amount");
            System.out.println(status);
            messageProducer.sendToPaymentSuccess(orderId);

            System.out.println("Received razorpay Webhook: "+event+" for order id: "+orderId+"payment id: "+paymentId+"for amount: "+amount);
            return ResponseEntity.ok("Webhook processed successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing webhook");
        }
    }

}
