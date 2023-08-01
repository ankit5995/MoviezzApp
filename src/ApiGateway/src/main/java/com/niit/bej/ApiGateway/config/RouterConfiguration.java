package com.niit.bej.ApiGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfiguration {
    @Bean
    public RouteLocator movieRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/userAuth/**").uri("http://localhost:8089/"))
                .route(p -> p.path("/userMovie/**").uri("http://localhost:8082/"))
                .route(p -> p.path("/api/v3/**").uri("http://localhost:8090/"))
                .build();
    }
}
