package com.everywhere.grpcservice.configuration;

import com.everywhere.grpcservice.GrpcServerLifecycle;
import com.everywhere.grpcservice.client.GreetingClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class MyConfiguration {
    @Bean
    public GrpcServerLifecycle buildServer() {
        return new GrpcServerLifecycle();
    }

    @Bean
    public GreetingClient buildClient() {
        return new GreetingClient();
    }
}
