package com.niit.bej.UserMovieService.config;

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
public class MessageConfiguration {

    private String exchangeName="movieService-exchange";
    private String queueName="movieService-queue";

    @Bean
    public DirectExchange directExchange()
    {
        return  new DirectExchange(exchangeName);
    }

    @Bean
    public Queue registerQueue()
    {
        return new Queue(queueName,true);
    }



    @Bean
    public Binding bindingExchangeAndQueue(DirectExchange directExchange,Queue queue)
    {
        return BindingBuilder.bind(queue).to(directExchange).with("movie-routing");
    }

    @Bean
    public Jackson2JsonMessageConverter movieJackson2JsonMessage()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate returnRabbitTemplate(final ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(movieJackson2JsonMessage());
        return rabbitTemplate;
    }



}
