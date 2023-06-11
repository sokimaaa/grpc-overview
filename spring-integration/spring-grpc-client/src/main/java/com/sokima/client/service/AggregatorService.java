package com.sokima.client.service;

import com.sokima.proto.ServerRequest;
import com.sokima.proto.ServerResponse;
import com.sokima.proto.ServerServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AggregatorService {

    @GrpcClient("server-service")
    private ServerServiceGrpc.ServerServiceBlockingStub stub;

    public List<?> doJob() {
        ServerRequest request = ServerRequest.newBuilder()
                .setField1("Spring Boot + gRPC")
                .build();
        ServerResponse response = stub.doJob(request);
        return response.getServerDtoList();
    }

}
