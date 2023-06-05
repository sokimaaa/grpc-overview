package com.sokima.grpc.service;

import com.sokima.grpc.observer.TransferStreamObserver;
import io.grpc.stub.StreamObserver;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {
    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferStreamObserver(responseObserver);
    }
}
