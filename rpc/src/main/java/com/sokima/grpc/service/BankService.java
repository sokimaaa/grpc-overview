package com.sokima.grpc.service;

import com.sokima.grpc.observer.CashStreamObserver;
import com.sokima.grpc.persistent.AccountDatabase;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import static java.lang.Thread.sleep;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
        int accountNumber = request.getAccountNumber();

        Balance balance = Balance.newBuilder()
                .setAmount(AccountDatabase.getBalance(accountNumber))
                .build();

        responseObserver.onNext(balance);
        responseObserver.onCompleted(); // have to close the connection
    }

    @Override
    public void withdraw(WithdrawalRequest request, StreamObserver<Money> responseObserver) {
        // we assume that bank has only ten dollar denomination
        int accountNumber = request.getAccountNumber();
        int amount = request.getAmount();
        int balance = AccountDatabase.getBalance(accountNumber);

        if (balance < amount) {
            StatusRuntimeException statusRuntimeException = Status.FAILED_PRECONDITION
                    .withDescription("Not enough money")
                    .asRuntimeException();
            responseObserver.onError(statusRuntimeException);
            return;
        }

        for (int i = 0; i < amount / 10; i++) {
            Money money = Money.newBuilder().setValue(10).build();
            responseObserver.onNext(money);
            AccountDatabase.deductBalanceAndReturn(accountNumber, 10);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        responseObserver.onCompleted();

    }

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashStreamObserver(responseObserver);
    }
}
