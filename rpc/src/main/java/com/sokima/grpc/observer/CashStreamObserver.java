package com.sokima.grpc.observer;

import com.sokima.grpc.persistent.AccountDatabase;
import com.sokima.grpc.service.Balance;
import com.sokima.grpc.service.DepositRequest;
import io.grpc.stub.StreamObserver;

public class CashStreamObserver implements StreamObserver<DepositRequest> {

    private StreamObserver<Balance> balanceStreamObserver;
    private int accountBalance;

    public CashStreamObserver(StreamObserver<Balance> balanceStreamObserver) {
        this.balanceStreamObserver = balanceStreamObserver;
    }

    @Override
    public void onNext(DepositRequest depositRequest) {
        int accountNumber = depositRequest.getAccountNumber();
        int amount = depositRequest.getAmount();
        this.accountBalance = AccountDatabase.addBalanceAndReturn(accountNumber, amount);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        Balance balance = Balance.newBuilder()
                .setAmount(accountBalance)
                .build();
        balanceStreamObserver.onNext(balance);
        balanceStreamObserver.onCompleted();
    }
}
