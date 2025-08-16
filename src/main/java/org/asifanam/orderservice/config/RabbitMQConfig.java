package org.asifanam.orderservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.asifanam.orderservice.orders.ApplicationProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private final ApplicationProperties properties;
    public RabbitMQConfig(ApplicationProperties properties) {
        this.properties = properties;
    }

    // Define RabbitMQ beans here using properties
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(properties.orderEventsExchange());
    }

    @Bean
    Queue newOrderQueue() {
        return new Queue(properties.newOrderQueue(), true);
    }
    @Bean
    Binding newOrderBinding(DirectExchange exchange, Queue newOrderQueue) {
        return BindingBuilder
                .bind(newOrderQueue())
                .to(exchange)
                .with(properties.newOrderQueue());
    }
    @Bean
    Queue deliveredOrderQueue() {
        return new Queue(properties.deliveredOrderQueue(), true);
    }

    @Bean
    Binding deliveredOrderBinding(DirectExchange exchange, Queue deliveredOrderQueue) {
        return BindingBuilder
                .bind(deliveredOrderQueue())
                .to(exchange)
                .with(properties.deliveredOrderQueue());
    }
    @Bean
    Queue cancelledOrderQueue() {
        return new Queue(properties.cancelledOrderQueue(), true);
    }
    @Bean
    Binding cancelledOrderBinding(DirectExchange exchange, Queue cancelledOrderQueue) {
        return BindingBuilder
                .bind(cancelledOrderQueue())
                .to(exchange)
                .with(properties.cancelledOrderQueue());
    }

    @Bean
    Queue errorOrderQueue() {
        return new Queue(properties.errorOrderQueue(), true);
    }

    @Bean
    Binding errorOrderBinding(DirectExchange exchange, Queue errorOrderQueue) {
        return BindingBuilder
                .bind(errorOrderQueue())
                .to(exchange)
                .with(properties.errorOrderQueue());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper objectMapper){
        final  var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter(objectMapper));
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jacksonConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
