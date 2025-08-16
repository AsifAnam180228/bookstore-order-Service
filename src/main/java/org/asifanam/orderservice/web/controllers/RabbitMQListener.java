package org.asifanam.orderservice.web.controllers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    @RabbitListener(queues = "${orders.new-order-queue}")
    public void handleNewOrder(String message) {
        // Process new order message
        System.out.println("Received new order: " + message);
    }

    @RabbitListener(queues = "${orders.delivered-order-queue}")
    public void handleDeliveredOrder(String message) {
        // Process delivered order message
        System.out.println("Received delivered order: " + message);
    }
}
