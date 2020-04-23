package com.everywhere.grpcservice;

import com.everywhere.grpcservice.service.GreetServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.SmartLifecycle;

import java.io.IOException;

public class GrpcServerLifecycle implements SmartLifecycle {
    private Server server;

    @Override
    public void start() {
        System.out.println("Hello gRPC");
        try {
            this.server = ServerBuilder
                    .forPort(8080)
                    .addService(new GreetServiceImpl())
                    .build();

            server.start();

            final Thread awaitThread = new Thread(() -> {
                try {
                    GrpcServerLifecycle.this.server.awaitTermination();
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            awaitThread.setDaemon(false);
            awaitThread.start();
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to start the grpc server", e);
        }
    }

    @Override
    public void stop() {
        Server localServer = this.server;
        if (localServer != null) {
            localServer.shutdown();
            this.server = null;
            System.out.println("gRPC server shutdown.");
        }
    }

    @Override
    public boolean isRunning() {
        return this.server != null && !this.server.isShutdown();
    }
}
