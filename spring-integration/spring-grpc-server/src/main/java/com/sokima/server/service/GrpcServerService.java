package com.sokima.server.service;

import com.sokima.proto.ServerDto;
import com.sokima.proto.ServerRequest;
import com.sokima.proto.ServerResponse;
import com.sokima.proto.ServerServiceGrpc;
import com.sokima.server.persistent.entity.GrpcServerEntity;
import com.sokima.server.persistent.repository.GrpcServerRepository;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class GrpcServerService extends ServerServiceGrpc.ServerServiceImplBase {

    private final GrpcServerRepository repository;

    @Override
    public void doJob(ServerRequest request, StreamObserver<ServerResponse> responseObserver) {
        String field1 = request.getField1();
        List<GrpcServerEntity> all = repository.findAll();
        ServerResponse.Builder builder = ServerResponse.newBuilder();

        all.stream()
                .filter(x -> x.getField2().byteValue() == field1.getBytes()[0])
                .map(x -> ServerDto.newBuilder()
                        .setField1(x.getField1())
                        .setField2(x.getField2())
                        .setField3(x.getField3())
                        .build())
                .forEach(builder::addServerDto);

        ServerResponse response = builder.build();
        if (response.getServerDtoList().isEmpty()) {
            StatusRuntimeException statusRuntimeException = Status.NOT_FOUND.asRuntimeException();
            responseObserver.onError(statusRuntimeException);
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
