package com.example.OnlineShop.listener;

import com.example.OnlineShop.event.OrderCompletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCompletedListener {

    @EventListener
    public void handleOrderCompleted(
            OrderCompletedEvent event
    ) {

        System.out.println(
                "Order completed: " +
                        event.getOrder().getId()
        );
    }
}