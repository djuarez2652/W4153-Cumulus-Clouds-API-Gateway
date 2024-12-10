package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@SpringBootApplication
public class GatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
            // Route to Microservice 1
            .route("microservice1", r -> r.path("/api/transactions/**")
                    .filters(f -> f.filter(new AuthHeaderFilter()))
                    .uri("https://microservice1.cloudprovider.com"))
            // Route to Microservice 2
            .route("microservice2", r -> r.path("/api/messaging/**")
                    .filters(f -> f.filter(new AuthHeaderFilter()))
                    .uri("https://microservice2.cloudprovider.com"))
            // Route to Microservice 3
            .route("microservice3", r -> r.path("/api/calendar/**")
                    .filters(f -> f.filter(new AuthHeaderFilter()))
                    .uri("https://microservice3.cloudprovider.com"))
            .build();
  }
}
