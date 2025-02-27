package com.example.paymentService.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class PaymentService {
    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    public Map<String,Object> createOrder(int amount, String currency)
            throws RazorpayException {
        Map<String, Object> response = new HashMap<>();
        try {
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100);
            orderRequest.put("currency", currency);
            System.out.println("payment service mei aake order create ho gyaa");

            Order order = razorpayClient.orders.create(orderRequest);

            //prepare response
            response.put("id",order.get("id"));
            response.put("amount",order.get("amount"));
            response.put("currency",order.get("currency"));
            response.put("status",order.get("status"));
            response.put("created_at",order.get("created_at"));
        }catch (Exception e){
            response.put("error","Failed to create order: "+e.getMessage());
        }
        return response;
    }
}
