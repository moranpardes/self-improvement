package com.everywhere.grpcservice.service;

import com.proto.GreetRequest;
import com.proto.GreetResponse;
import com.proto.GreetServiceGrpc;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {
    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        GreetResponse response = GreetResponse.newBuilder()
                .setResult("Hello " + request.getGreeting().getFirstName()).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
