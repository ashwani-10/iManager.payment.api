package com.example.paymentService.service;

import com.example.paymentService.exceptions.FailureProducingMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    @Autowired
    KafkaProducerService kafkaProducer;

    public void sendToPaymentSuccess(String orderId,Integer amount) throws JsonProcessingException {
        final String topic = "Payment-Success";

        int retries = 3;

        while (retries-- > 0) {
            try {
                kafkaProducer.produceMessage(amount/100,orderId,topic);
                return;
            }catch (Exception e) {
                if (retries == 0) {
                    throw new FailureProducingMessage("Failed to send registration email after 3 retries", e);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new FailureProducingMessage("Email sending Interrupted", ex);
                }
            }
        }
    }
}
