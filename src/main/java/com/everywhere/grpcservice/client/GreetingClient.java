package com.everywhere.grpcservice.client;

import com.proto.GreetRequest;
import com.proto.GreetResponse;
import com.proto.GreetServiceGrpc;
import com.proto.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.SmartLifecycle;
import org.springframework.scheduling.annotation.Scheduled;

public class GreetingClient implements SmartLifecycle {
    private ManagedChannel channel;
    private GreetServiceGrpc.GreetServiceBlockingStub client;

    @Override
    public void start() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        this.client = GreetServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public void stop() {
        channel.shutdown();
    }

    @Override
    public boolean isRunning() {
        return this.channel != null && !channel.isShutdown();
    }

    @Scheduled(fixedRate = 1000)
    public void callGreet() {
        GreetResponse response = client.greet(GreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                        .setFirstName("Moran")
                        .setLastName("Pardes")
                        .build())
                .build());

        System.out.println("response: " + response.getResult());
    }
}
