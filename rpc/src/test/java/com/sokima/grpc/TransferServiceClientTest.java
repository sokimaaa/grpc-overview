package com.sokima.grpc;

import com.sokima.grpc.service.TransferRequest;
import com.sokima.grpc.service.TransferResponse;
import com.sokima.grpc.service.TransferServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransferServiceClientTest {

    private TransferServiceGrpc.TransferServiceBlockingStub blockingStub;
    private TransferServiceGrpc.TransferServiceStub stub;

    @BeforeAll
    public void setUp() {
        ManagedChannel localhost = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        blockingStub = TransferServiceGrpc.newBlockingStub(localhost);
        stub = TransferServiceGrpc.newStub(localhost);
    }

    @Test
    void transferTest() {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<TransferRequest> request = stub.transfer(new StreamObserver<>() {
            @Override
            public void onNext(TransferResponse transferResponse) {
                System.out.println("Status : " + transferResponse.getStatus());
                transferResponse.getAccountsList()
                        .forEach(
                                account -> System.out.println("Account : " + account.getAccountNumber()
                                ));
            }

            @Override
            public void onError(Throwable throwable) {
                latch.countDown();
                throw Status.fromThrowable(throwable).asRuntimeException();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });

        generateRequest(request, 50);
        request.onCompleted();

        try {
            latch.await();
        } catch (InterruptedException e) {
            fail();
        }
    }

    private static void generateRequest(StreamObserver<TransferRequest> request, int requestCount) {
        Random random = new Random();
        for (int i = 0; i < requestCount; i++) {
            request.onNext(
                    TransferRequest.newBuilder()
                            .setAmount(random.nextInt(10, 100))
                            .setToAccount(random.nextInt(1, 25))
                            .setFromAccount(random.nextInt(1, 25))
                            .build());
        }
    }

}
