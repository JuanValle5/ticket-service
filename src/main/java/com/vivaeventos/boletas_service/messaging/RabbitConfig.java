package com.vivaeventos.boletas_service.messaging;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange ticketExchange() {

        return new TopicExchange(
                RabbitConstants.TICKET_EXCHANGE
        );
    }

    @Bean
    public Queue ticketCreatedQueue() {

        return new Queue(
                RabbitConstants.TICKET_CREATED_QUEUE
        );
    }

    @Bean
    public Binding ticketCreatedBinding(
            Queue ticketCreatedQueue,
            TopicExchange ticketExchange
    ) {

        return BindingBuilder
                .bind(ticketCreatedQueue)
                .to(ticketExchange)
                .with(
                        RabbitConstants
                                .TICKET_CREATED_ROUTING_KEY
                );
    }

    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            JacksonJsonMessageConverter converter
    ) {

        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);

        return factory;
    }
}