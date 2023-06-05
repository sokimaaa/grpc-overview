package com.sokima.grpc;

import com.sokima.grpc.service.BankService;
import com.sokima.grpc.service.TransferService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class RpcServer {

    public static void main(String[] args) {
        Server server = ServerBuilder.forPort(9090)
                .addService(new BankService())
                .addService(new TransferService())
                .build();

        try {
            server.start();
            server.awaitTermination();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
