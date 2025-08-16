package org.asifanam.orderservice.orders;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "orders")
public record ApplicationProperties(
        String orderEventsExchange,
        String newOrderQueue,
        String deliveredOrderQueue,
        String cancelledOrderQueue,
        String errorOrderQueue
) {
}
