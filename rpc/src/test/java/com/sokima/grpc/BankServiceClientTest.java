package com.sokima.grpc;

import com.sokima.grpc.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankServiceClientTest {

    private BankServiceGrpc.BankServiceBlockingStub blockingStub;
    private BankServiceGrpc.BankServiceStub stub;

    @BeforeAll
    public void setUp() {
        ManagedChannel localhost = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        blockingStub = BankServiceGrpc.newBlockingStub(localhost);
        stub = BankServiceGrpc.newStub(localhost);
    }

    @Test
    void getBalanceTest() {
        BalanceCheckRequest request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(10)
                .build();

        Balance balance = blockingStub.getBalance(request);

        final int expectedBalanceAmount = 25 * request.getAccountNumber();

        Assertions.assertEquals(expectedBalanceAmount, balance.getAmount());
    }

    @Test
    void withdrawalTest() {
        WithdrawalRequest request = WithdrawalRequest.newBuilder()
                .setAccountNumber(10)
                .setAmount(100)
                .build();

        AtomicInteger responseCount = new AtomicInteger(0);
        blockingStub.withdraw(request).forEachRemaining(
                money -> {
                    responseCount.getAndIncrement();
                    Assertions.assertEquals(10, money.getValue());
                }
        );

        Assertions.assertEquals(10, responseCount.get());
    }

    @Test
    void withdrawalErrorTest() {
        WithdrawalRequest request = WithdrawalRequest.newBuilder()
                .setAccountNumber(10)
                .setAmount(100_000)
                .build();

        try {
            blockingStub.withdraw(request).next();
            fail();
        } catch (StatusRuntimeException ex) {
        }
    }

    @Test
    void withdrawalAsyncTest() {
        CountDownLatch latch = new CountDownLatch(1);

        WithdrawalRequest request = WithdrawalRequest.newBuilder()
                .setAccountNumber(10)
                .setAmount(100)
                .build();

        stub.withdraw(request, new StreamObserver<>() {
            @Override
            public void onNext(Money money) {
                System.out.println(money);
                Assertions.assertEquals(10, money.getValue());
            }

            @Override
            public void onError(Throwable throwable) {
                latch.countDown();
                throw Status.fromThrowable(throwable).asRuntimeException();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
                System.out.println("Server is done!");
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    void cashDepositTest() {
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<DepositRequest> streamObserver = stub.cashDeposit(new StreamObserver<>() {
            @Override
            public void onNext(Balance balance) {
                System.out.println(balance);
            }

            @Override
            public void onError(Throwable throwable) {
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });

        for (int i = 0; i < 10; i++) {
            DepositRequest request = DepositRequest.newBuilder()
                    .setAccountNumber(8)
                    .setAmount(10)
                    .build();
            streamObserver.onNext(request);
        }

        streamObserver.onCompleted();

        try {
            latch.await();
        } catch (InterruptedException e) {
            fail();
        }
    }
}
